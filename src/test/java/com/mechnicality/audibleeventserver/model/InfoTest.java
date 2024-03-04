package com.mechnicality.audibleeventserver.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class InfoTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void newInfoTest() {
        Info uut = new InfoBuilder().with( $-> {
            $.sender = IpAddress.of("192.168.143.109");
            $.connected = true;
            $.enabled = false;
            $.onThreshold = 1233;
            $.offThreshold = 12;
            $.lastConnected = Instant.parse("2024-01-01T00:00:00Z");
        }).createInfo();
        assertEquals("192.168.143.109", uut.sender.toString());
        assertTrue(uut.connected);
        assertFalse(uut.enabled);
        assertEquals(1233, uut.onThreshold);
        assertEquals(12, uut.offThreshold);
        assertEquals("2024-01-01T00:00:00Z", uut.lastConnected.toString());
    }
}