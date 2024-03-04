package com.mechnicality.audibleeventserver.service;

import com.mechnicality.audibleeventserver.model.packet.AudioPacket;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Queue;
import java.util.concurrent.Callable;

public class UdpListenerService implements Callable<String> {

    public static final int AUDIO_EVENT_PACKET_SIZE = 12 + 8 * 64 * 2;

    private static final Logger logger = LoggerFactory.getLogger(UdpListenerService.class);
    private final int receivePort;
    private final Queue<AudioPacket> eventQueue;

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[AUDIO_EVENT_PACKET_SIZE];

    public UdpListenerService(
            Queue<AudioPacket> eventQueue, int receivePort) {
        this.receivePort = receivePort;
        this.eventQueue = eventQueue;
    }

    @PostConstruct
    public void init() {
        try {
            socket = new DatagramSocket(receivePort);

        } catch (Exception e) {
            logger.error("Failed to create socket at {} with error {}", receivePort, e.getMessage());
            throw new RuntimeException(e);
        }
        logger.info("Started UdpListenerService");
    }

    public void stop() {
        this.running = false;
    }

    @Override
    public String call() {
        running = true;
        try {
            while (running) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                try {
                    // block here until a packet is received.
                    socket.receive(packet);
                } catch (IOException e) {
                    logger.error("Socket receive failed! {} ", e.getMessage());

                    throw new RuntimeException(e);
                }

 //               InetAddress address = packet.getAddress();
 //               int port = packet.getPort();
 //               packet = new DatagramPacket(buf, buf.length, address, port);
                try {
                    AudioPacket audioPacket = AudioPacket.of(packet.getData(),packet.getLength());
                    if (!eventQueue.offer(audioPacket)) {
                        logger.warn("Sample queue is full, dropping sample");
                    } else {
                        logger.info("Added event to queue - seq {}.", audioPacket.getSequence());
                    }
                } catch (RuntimeException r) {
                    logger.warn("Could not decode packet to AudioEvent - {}", r.getMessage(), r);
                }
            }
        } catch (RuntimeException r) {
            this.running = false;
        } catch(Throwable t) {
            logger.error("Unexpected Exception {}", t.getMessage(), t);
        } finally {
            running = false;
            socket.close();
        }
        return this.getClass().getSimpleName() + " has finished";

    }

}
