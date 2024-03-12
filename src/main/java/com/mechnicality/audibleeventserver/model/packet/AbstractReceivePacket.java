package com.mechnicality.audibleeventserver.model.packet;

import com.mechnicality.audibleeventserver.model.PacketType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Optional;

public abstract class AbstractReceivePacket<T> implements ReceivePacket<T> {
    public static final int HEADER_BYTES = 12;
    public static final int COMMAND_OFFSET = 0;
    public static final int COUNT_OFFSET = 8;
    public static final int SEQUENCE_OFFSET = 4;
    public static final int COMMAND_MASK = 0xf;

    public static class PacketContext {
        PacketType packetType;
        int count;
        int sequence;

        ByteBuffer byteBuffer;
        public PacketContext(byte[] bytes, int length) {
            assert length >= HEADER_BYTES; // header size
            byteBuffer = ByteBuffer.allocate(length);
            byteBuffer.put(bytes, 0, length); // header only
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            byteBuffer.rewind();
            int command = byteBuffer.getInt(COMMAND_OFFSET);
            int count = byteBuffer.getInt(COUNT_OFFSET);
            int sequence = byteBuffer.getInt(SEQUENCE_OFFSET);

            Optional<PacketType> packetType = PacketType.getFromCode(command & COMMAND_MASK);
            if(packetType.isEmpty()) {
                throw new RuntimeException("Invalid packet type found in 'command' " + command);
            }
            this.packetType = packetType.get();
            this.sequence = sequence;
            this.count = count;
        }
    }

    private final PacketType packetType;
    private final int sequence;

    private final int size;

    protected AbstractReceivePacket(PacketType packetType, int sequence, int size){
        this.packetType = packetType;
        this.sequence = sequence;
        this.size = size;
    }

    @Override
    public PacketType getPacketType() {
        return packetType;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int getSequence() {
        return sequence;
    }

    public abstract T getValue();


}
