package com.mechnicality.audibleeventserver.model.packet;

import com.mechnicality.audibleeventserver.model.PacketType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
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
        assertEquals("08000d00aa55aa5548656c6c6f",out);

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
        assertEquals(
                "07001b0048656c6c6f20546865726501010000426c616840e20100", out
        );
    }

    @Test
    void integerAtOf() {
        uut = ControlPacket.of(b ->
                b
                        .type(PacketType.Error)
                        .integerAt(4,1122)
                        .integerAt(12,5566)
                        .integerAt(8,3344)
                );
        assertEquals((short)16,uut.getSize());
        assertEquals(PacketType.Error, uut.getType());
        assertEquals(16, uut.getPayload().length);
        String asHex = asHex(uut);
        System.out.println(asHex);
        assertEquals("0200100062040000100d0000be150000",asHex);
    }


    private String asHex(ControlPacket cp) {
        byte[] bytes = cp.getPayload();
        StringBuilder str = new StringBuilder();
        for (byte b : bytes) {
            str.append(String.format("%02x", b));
        }
        return str.toString();
    }

}