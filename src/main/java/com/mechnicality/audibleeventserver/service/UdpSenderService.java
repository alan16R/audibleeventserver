package com.mechnicality.audibleeventserver.service;

import com.mechnicality.audibleeventserver.model.Info;
import com.mechnicality.audibleeventserver.model.packet.ControlPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.*;

public class UdpSenderService {
    private static final Logger logger = LoggerFactory.getLogger(UdpSenderService.class);
    private final String remoteHost;
    private final int remotePort;



    private DatagramSocket datagramSocket;
    public UdpSenderService(String remoteHost, int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
        try {
            this.datagramSocket = new DatagramSocket();

        } catch (SocketException s) {
            logger.error("Could not create socket {}", s.getMessage());

        }
    }

    public void enable(boolean onOff) {
    }

    public Info getInfo(InetAddress ipAddress) {
        // create Info packet and send it.
        return null;
    }

    public void sendPacket(ControlPacket controlPacket) {
        try {
            InetAddress destination = InetAddress.getByName(remoteHost);
            DatagramPacket datagramPacket = new DatagramPacket(controlPacket.getPayload(), 0, controlPacket.getSize(),
                    destination, remotePort);
            datagramSocket.send(datagramPacket);

        } catch (UnknownHostException e) {
            logger.error("Could not send datagram because {} is not recognized", remoteHost);
        } catch (IOException e) {
            logger.error("Failed to send packet of type: {}", controlPacket.getType());
        }
    }
}
