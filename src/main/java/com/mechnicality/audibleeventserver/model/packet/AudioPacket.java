package com.mechnicality.audibleeventserver.model.packet;

import com.mechnicality.audibleeventserver.model.PacketType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * AudioPacket commandStatus field:-
 *
 * Bits 0 - 3 are the message type.
 *
 *
 *
 */



@Getter
public class AudioPacket extends AbstractReceivePacket<List<Short>> {

    private final List<Short> value;
    private final short max;
    private final short min;

    private AudioPacket(PacketType packetType, int sequence, int size, List<Short> value, short max, short min) {
        super(packetType, sequence, size);
        this.value = value;
        this.max = max;
        this.min = min;
    }

    public static AudioPacket of(byte[] bytes, int length) {
        PacketContext packetContext = new PacketContext(bytes, length);
        short max = Short.MIN_VALUE,min = Short.MAX_VALUE;
        List<Short> values = new ArrayList<>();
        if (length > HEADER_BYTES) {
            for (int i = HEADER_BYTES; i < length; i+=2) {
                short value = packetContext.byteBuffer.getShort(i);
                if (value < min) {
                    min = value;
                }
                if (value > max) {
                    max = value;
                }
                values.add(value);
            }
        }
        return new AudioPacket(
                packetContext.packetType,
                packetContext.sequence,
                packetContext.count, values, max, min);
    }

    @Override
    public List<Short> getValue() {
        return value;
    }

    @Override
    public byte[] getBytes() {
        return new byte[0]; // for the moment.
    }

    public short getMax() {
        return max;
    }

    public short getMin() {
        return min;
    }
}
