package com.mechnicality.audibleeventserver.model.packet;

import com.mechnicality.audibleeventserver.model.PacketType;

public class IdPacket implements TxPacket {


    @Override
    public PacketType getType() {
        return PacketType.Wakeup;
    }

    @Override
    public short getSize() {
        return Integer.valueOf(4).shortValue();
    }

    @Override
    public byte[] getPayload() {
        return new byte[0];
    }
}
