package com.mechnicality.audibleeventserver.service;

import com.mechnicality.audibleeventserver.model.Info;
import com.mechnicality.audibleeventserver.model.packet.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UdpSenderService {
    private static final Logger logger = LoggerFactory.getLogger(UdpSenderService.class);
    private final String remoteHost;
    private final int remotePort;

    public UdpSenderService(String remoteHost, int remotePort) {
        this.remoteHost = remoteHost;
        this.remotePort = remotePort;
    }

    public void enable(boolean onOff) {
    }

    public Info getInfo(InetAddress ipAddress) {
        // create Info packet and send it.
        return null;
    }

    public void sendPacket(Packet<?> packet) {
        try {
            InetAddress destination = InetAddress.getByName(remoteHost);
            DatagramPacket datagramPacket = new DatagramPacket(packet.getBytes(), 0, packet.getBytes().length,
                    destination, remotePort);
        } catch (UnknownHostException e) {
            logger.error("Could not send datagram because {} is not recognized", remoteHost);
        }
    }
}
