package com.mechnicality.audibleeventserver.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;

import static java.net.InetAddress.getByName;
import static org.junit.jupiter.api.Assertions.*;

class InfoTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void newInfoTest() {
        Info uut = new InfoBuilder().with( $-> {
            try {
                $.sender =  getByName("192.168.143.109");
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
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