package com.mechnicality.audibleeventserver.model.packet;

import com.mechnicality.audibleeventserver.model.PacketType;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

public class ControlPacket {
    private  static int MAX_CONTROL_PACKET = 1530;
    private final PacketType type;
    private final Short size;
    private final byte[] payload;

    private ControlPacket(Builder builder){
        payload = new byte[builder.size];
        for (int i = 0; i< builder.size; i++){
            payload[i] = builder.payload.get(i);
        }
        type = builder.packetType;
        size = builder.size;
    }

    public PacketType getType() {
        return type;
    }

    public Short getSize() {
        return size;
    }

    public byte[] getPayload() {
        byte[] toReturn = new byte[size];
        for (int i = 0; i< size; i++) {
            toReturn[i] = payload[i];
        }
        return toReturn;
    }

    public static class Builder {
        private PacketType packetType;

        private short size;
        private ByteBuffer payload;

        private Builder() {
            payload = ByteBuffer.allocate(MAX_CONTROL_PACKET);
            this.payload.order(ByteOrder.LITTLE_ENDIAN);
            // 2 bytes for the packetType, 2 bytes for the size;
            payload.position(4);
        }

        public Builder type(PacketType type) {
            this.packetType = type;
            return this;
        }

         public Builder text(String text) {
            if (text != null) {
                byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
                this.payload.put(bytes);
            }
            return this;
        }

        public Builder integer(int integer) {
            payload.putInt(integer);
            return this;
        }

        public Builder integerAt(int index, int integer) {
            payload.putInt(index,integer);
            return this;
        }


        public ControlPacket build() {

            this.size = Integer.valueOf(this.payload.position()).shortValue();
            this.payload.put(0,Integer.valueOf(packetType.ordinal()).byteValue());
            this.payload.putShort(2, size);
            this.payload.limit(this.size);
            return new ControlPacket(this);
        }
    }

    public static ControlPacket of( Function<Builder, Builder> fn){
        return (fn.apply(new ControlPacket.Builder())).build();
    }
}
