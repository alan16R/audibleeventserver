package com.mechnicality.audibleeventserver.model.packet;

import com.mechnicality.audibleeventserver.model.PacketType;

public interface TxPacket {

    PacketType getType();

    short getSize(); // total number of bytes in the packet

    byte[] getPayload();


}
