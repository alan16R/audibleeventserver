package com.mechnicality.audibleeventserver;

import com.mechnicality.audibleeventserver.model.packet.AudioPacket;
import com.mechnicality.audibleeventserver.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

@Configuration
public class AudibleEventServerConfiguration {

    @Value("${udp.audio.rx.port}")
    private int receivePort;

    @Value("${udp.events.files}")
    private String eventFilesPath;

    @Value("${udp.remote.host}")
    private String remoteHost;

    @Value("${udp.remote.tx.port}")
    private int remotePort;


    /**
     * We can buffer up to 5000 events in memory which is probably overkill.
     * @return The AudioEventQueue.
     */
    @Bean
    public Queue<AudioPacket> audioEventQueue() {
        return new ArrayBlockingQueue<>(5000);
    }

    @Bean
    public UdpListenerService udpListenerService() {
        return new UdpListenerService(
            audioEventQueue(),
            receivePort
        );
    }

    @Bean
    public QueueManager queueManager() {
        return new QueueManager(eventWriter(),audioEventQueue());
    }

    @Bean
    public AudioEventWriter eventWriter() {
        return new AudioEventWriter(eventFilesPath);
    }

    @Bean
    public TaskRunner taskRunner() {
        return new TaskRunner(queueManager(), udpListenerService());
    }

    @Bean
    public UdpSenderService udpSenderService() {
        return new UdpSenderService(remoteHost, remotePort);
    }

}
