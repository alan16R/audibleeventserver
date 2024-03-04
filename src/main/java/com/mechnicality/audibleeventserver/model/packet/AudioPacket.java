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
public class AudioPacket extends AbstractPacket<List<Short>> {

    private final List<Short> value;

    private AudioPacket(PacketType packetType, int sequence, int size, List<Short> value) {
        super(packetType, sequence, size);
        this.value = value;
    }

    public static AudioPacket of(byte[] bytes, int length) {
        PacketContext packetContext = new PacketContext(bytes, length);
        List<Short> values = new ArrayList<>();
        if (length > HEADER_BYTES) {

            for (int i = HEADER_BYTES; i < length; i+=2) {
                values.add(packetContext.byteBuffer.getShort(i));
            }
        }
        return new AudioPacket(
                packetContext.packetType,
                packetContext.sequence,
                packetContext.count, values);
    }

    @Override
    public List<Short> getValue() {
        return value;
    }

    @Override
    public byte[] getBytes() {
        return new byte[0]; // for the moment.
    }

}
