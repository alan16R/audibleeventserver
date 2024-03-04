package com.mechnicality.audibleeventserver.service;

import com.mechnicality.audibleeventserver.model.Info;
import com.mechnicality.audibleeventserver.model.IpAddress;
import com.mechnicality.audibleeventserver.model.packet.Packet;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class UdpSenderService {
    public void enable(boolean onOff) {
    }

    public Info getInfo(IpAddress ipAddress, Integer port) {
        return null;
    }

    public void sendPacket(Packet<?> packet, InetAddress destination, int port) {
        DatagramPacket datagramPacket = new DatagramPacket(packet.getBytes(), 0, packet.getBytes().length,
                destination, port);

    }

}
