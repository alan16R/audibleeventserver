package com.mechnicality.audibleeventserver.model.packet;

import com.mechnicality.audibleeventserver.model.PacketType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.*;

class ControlPacketTest {

    private ControlPacket uut;
    private int intValue = 0x55aa55aa;
    private String testStr = "Hello";
    @BeforeEach
    void setUp() {
        uut = ControlPacket.of(b ->
                b
                        .type(PacketType.Id)
                        .integer(intValue)
                        .text(testStr)

        );
    }

    @Test
    void getType() {
        assertEquals(PacketType.Id, uut.getType());
    }

    @Test
    void getSize() {
        assertEquals((short)13,uut.getSize());
    }

    @Test
    void getPayload() {
        byte[] found = uut.getPayload();
        assertEquals(13, found.length);
        String out = this.asHex(uut);
        System.out.println(out);
        assertEquals("80d0aa55aa5548656c6c6f",out);

    }

    @Test
    void of() {
        uut = ControlPacket.of(b -> b
                .text("Hello There") // 4 to 15
                .type(PacketType.Info)
                .integer(257) // 16 to 20
                .text("Blah") // 21 to 24
                .integer(123456) //25-28
        );
        short size = uut.getSize();
        assertEquals((short)27, uut.getSize());
        String out = this.asHex(uut);
        System.out.println(out);
        assertEquals(out,
                "701b048656c6c6f2054686572651100426c616840e210"
        );
    }

    private String asHex(ControlPacket cp) {
        byte[] found = cp.getPayload();
        String out = "";
        for (byte b : found) {
            out = out + String.format("%x",(byte)b);
        }
        return out;
    }
}