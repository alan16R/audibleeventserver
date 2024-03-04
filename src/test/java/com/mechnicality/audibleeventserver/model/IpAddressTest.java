package com.mechnicality.audibleeventserver.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IpAddressTest {

    @Test
    void testToStringAndOf() {
        IpAddress ipAddress = IpAddress.of("192.168.140.209");
        assertEquals("192.168.140.209", ipAddress.toString());
    }

    @Test
    void getAsShort() {
        IpAddress ipAddress = IpAddress.of("192.168.140.209");
        assertEquals((short)192, ipAddress.getAsShort().get(0));
        assertEquals((short)168, ipAddress.getAsShort().get(1));
        assertEquals((short)140, ipAddress.getAsShort().get(2));
        assertEquals((short)209, ipAddress.getAsShort().get(3));
    }

    @Test
    void testValidate() {
        IpAddress uut = IpAddress.of("192.168.140.209");

        uut.toString();




    }

}