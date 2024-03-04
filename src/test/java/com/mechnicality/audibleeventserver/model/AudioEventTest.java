package com.mechnicality.audibleeventserver.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mechnicality.audibleeventserver.model.packet.AudioPacket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AudioEventTest {

    private AudioEvent uut;
    private AudioPacket audioPacket, secondPacket;

    byte[] packetData = {
            (byte) 0x0,(byte) 0x0,(byte) 0x0,(byte) 0x0,
            (byte) 0x0,(byte) 0x1,(byte) 0x0,(byte) 0x0,
            (byte) 0x80,(byte) 0xCC,(byte) 0x81,(byte) 0x4D,

            (byte) 0x1,(byte) 0x1,(byte) 0x2D,(byte) 0xF8,(byte) 0x75,(byte) 0xB,(byte) 0xB2,(byte) 0xFE,
            (byte) 0x4, (byte) 0x1A,(byte) 0x2,(byte) 0x2A, (byte) 0xF2,(byte) 0xF3,(byte) 0xAB,(byte) 0x42,
            (byte) 0xCD,(byte) 0x40, (byte) 0xB8,(byte) 0x88,(byte) 0xD0,(byte) 0x95,(byte) 0xD6,(byte) 0x63,
            (byte) 0x80,(byte) 0x3F,(byte) 0xBB, (byte) 0xFA,(byte) 0xA8,(byte) 0x23,(byte) 0xB6,(byte) 0x94,

            (byte) 0xD,(byte) 0x54,(byte) 0x24,(byte) 0x31, (byte) 0x4D,(byte) 0x1C,(byte) 0x0,(byte) 0xEC,
            (byte) 0x16,(byte) 0xB8,(byte) 0xAF,(byte) 0xCA,(byte) 0x18, (byte) 0xEB,(byte) 0xC8,(byte) 0xE3,
            (byte) 0xFA,(byte) 0x9E,(byte) 0xD4,(byte) 0x12,(byte) 0xE9,(byte) 0xAF, (byte) 0x6B,(byte) 0xA,
            (byte) 0xAA,(byte) 0xCD,(byte) 0x76,(byte) 0xFD, (byte) 0x22,(byte) 0x59, (byte) 0x28, (byte) 0x25,


            (byte) 0x97,(byte) 0x7,(byte) 0x58,(byte) 0x78,(byte) 0x22,(byte) 0xA9,(byte) 0x34,(byte) 0xF7,
            (byte) 0x25,(byte) 0x81,(byte) 0x6B,(byte) 0x6E,(byte) 0xED,(byte) 0x59,(byte) 0x62,(byte) 0xBB,
            (byte) 0x9B, (byte) 0x3F,(byte) 0x37,(byte) 0x6D, (byte) 0xDF,(byte) 0xDE,(byte) 0x62,(byte) 0xBB,
            (byte) 0xF,(byte) 0xF3,(byte) 0xA3,(byte) 0x7A,(byte) 0x24,(byte) 0xDF,(byte) 0x47,(byte) 0x5D,

            (byte) 0x63,(byte) 0xAA,(byte) 0x29,(byte) 0x6,(byte) 0x84,(byte) 0x7D,(byte) 0x60,(byte) 0x85,
            (byte) 0xCF,(byte) 0x41,(byte) 0x44,(byte) 0x78, (byte) 0x82,(byte) 0x46,(byte) 0x76,(byte) 0xE8,
            (byte) 0x1E,(byte) 0xB1,(byte) 0x61,(byte) 0x32,(byte) 0x8C,(byte) 0xA3,(byte) 0xD1,(byte) 0xD7,
            (byte) 0xE1,(byte) 0x43,(byte) 0xC6,(byte) 0x73,(byte) 0x16,(byte) 0xD5,(byte) 0x17,(byte) 0x8A,


            (byte) 0xA5,(byte) 0x5A,(byte) 0x52,(byte) 0x36, (byte) 0xA4,(byte) 0xE6,(byte) 0x97,(byte) 0x42,
            (byte) 0xAA,(byte) 0x7A,(byte) 0x79,(byte) 0x8F,(byte) 0x9D,(byte) 0xD4,(byte) 0x86,(byte) 0xDB,
            (byte) 0x96,(byte) 0x5,(byte) 0xC9,(byte) 0xD9,(byte) 0x1,(byte) 0x18,(byte) 0xEC,(byte) 0x74,
            (byte) 0x14,(byte) 0xAF,(byte) 0xEB,(byte) 0x8F, (byte) 0x94,(byte) 0x6F,(byte) 0x5A,(byte) 0x25,

            (byte) 0x6C,(byte) 0x26,(byte) 0x35,(byte) 0x0,(byte) 0x21,(byte) 0x66,(byte) 0xE1,(byte) 0xD7,
            (byte) 0xEE,(byte) 0x9,(byte) 0x49,(byte) 0x63,(byte) 0xBC,(byte) 0x52,(byte) 0x30,(byte) 0xA1,
            (byte) 0x17,(byte) 0x53,(byte) 0x78,(byte) 0xCF, (byte) 0x5B,(byte) 0xF9,(byte) 0x35,(byte) 0xBF,
            (byte) 0x79,(byte) 0x43,(byte) 0x49,(byte) 0x11,(byte) 0xF7,(byte) 0xCF,(byte) 0x89,(byte) 0x25,


            (byte) 0xD1,(byte) 0x20,(byte) 0x4B,(byte) 0x6D,(byte) 0x52,(byte) 0x76,(byte) 0x82,(byte) 0x14,
            (byte) 0x40,(byte) 0x4B,(byte) 0xE7,(byte) 0x37, (byte) 0x17,(byte) 0x7,(byte) 0xDC,(byte) 0xAA,
            (byte) 0x6E,(byte) 0x26,(byte) 0xF,(byte) 0xFE,(byte) 0x20,(byte) 0x22,(byte) 0xF,(byte) 0x1D,
            (byte) 0x23,(byte) 0x27,(byte) 0x5,(byte) 0xD5,(byte) 0x3B,(byte) 0x1,(byte) 0xE3,(byte) 0x37,

            (byte) 0x8C,(byte) 0x2D,(byte) 0xCE,(byte) 0x2A, (byte) 0x2E,(byte) 0x37,(byte) 0x7B,(byte) 0xC0,
            (byte) 0xD7,(byte) 0xE8,(byte) 0xDD,(byte) 0x18,(byte) 0x2D,(byte) 0xF0,(byte) 0xEE,(byte) 0xC6,
            (byte) 0xEB,(byte) 0x1,(byte) 0x73,(byte) 0xC,(byte) 0xE1,(byte) 0x44,(byte) 0x18,(byte) 0x96,
            (byte) 0xC7,(byte) 0x45,(byte) 0x53,(byte) 0x9F, (byte) 0x42,(byte) 0xBE,(byte) 0xFA,(byte) 0x85,


            (byte) 0x1C,(byte) 0x53,(byte) 0xFD,(byte) 0x1B,(byte) 0x7D,(byte) 0x4F,(byte) 0x2D,(byte) 0x35,
            (byte) 0x76,(byte) 0x88,(byte) 0xD1,(byte) 0x60,(byte) 0x51,(byte) 0x2D,(byte) 0xBF,(byte) 0xEF,
            (byte) 0xE4,(byte) 0x35,(byte) 0x35,(byte) 0xE4, (byte) 0x18,(byte) 0xB7,(byte) 0xF9,(byte) 0xF3,
            (byte) 0x31,(byte) 0xE4,(byte) 0x5D,(byte) 0x1C,(byte) 0xBF,(byte) 0x43,(byte) 0x4A,(byte) 0x39,

            (byte) 0xF2,(byte) 0xBE,(byte) 0xC0,(byte) 0x38,(byte) 0xF7,(byte) 0x94,(byte) 0x2E,(byte) 0x30,
            (byte) 0xE9,(byte) 0x94,(byte) 0x66,(byte) 0xB6, (byte) 0x9A,(byte) 0xEA,(byte) 0x64,(byte) 0x14,
            (byte) 0x13,(byte) 0xC6,(byte) 0x9D,(byte) 0xF5,(byte) 0xB4,(byte) 0xC5,(byte) 0x9F,(byte) 0xE,
            (byte) 0xAA,(byte) 0x5C,(byte) 0x9C,(byte) 0x8F,(byte) 0xD2,(byte) 0x6A,(byte) 0x30,(byte) 0x85,


            (byte) 0xB,(byte) 0xDA,(byte) 0xB6,(byte) 0xA4, (byte) 0xA8,(byte) 0x94,(byte) 0x56,(byte) 0x16,
            (byte) 0x66,(byte) 0xED,(byte) 0x6A,(byte) 0x7,(byte) 0xCD,(byte) 0xA4,(byte) 0x3,(byte) 0x3D,
            (byte) 0x71,(byte) 0xB2,(byte) 0xFA,(byte) 0x35,(byte) 0x44,(byte) 0x4B,(byte) 0x5,(byte) 0x3C,
            (byte) 0xE1,(byte) 0x16,(byte) 0x54,(byte) 0x83, (byte) 0x12,(byte) 0xCC,(byte) 0xC5,(byte) 0xD5,

            (byte) 0x6,(byte) 0xE3,(byte) 0xDC,(byte) 0xE0,(byte) 0xA6,(byte) 0xC6,(byte) 0xDC,(byte) 0x86,
            (byte) 0xA2,(byte) 0x8B,(byte) 0x59,(byte) 0x49,(byte) 0x2D,(byte) 0x91,(byte) 0x26,(byte) 0x29,
            (byte) 0xF3,(byte) 0x66,(byte) 0x8E,(byte) 0xA0, (byte) 0x9A,(byte) 0xFF,(byte) 0xDC,(byte) 0x67,
            (byte) 0x6,(byte) 0xA1,(byte) 0xFB,(byte) 0xC4,(byte) 0xAD,(byte) 0xE1,(byte) 0xD1,(byte) 0x14,


            (byte) 0x1C,(byte) 0x9,(byte) 0x41,(byte) 0x62,(byte) 0xEC,(byte) 0x80,(byte) 0x69,(byte) 0xAB,
            (byte) 0x8D,(byte) 0x83,(byte) 0x63,(byte) 0x35, (byte) 0xBA,(byte) 0x16,(byte) 0xFD,(byte) 0x7D,
            (byte) 0x74,(byte) 0xD8,(byte) 0xF3,(byte) 0x2D,(byte) 0x9A,(byte) 0xA5,(byte) 0x80,(byte) 0x64,
            (byte) 0xC,(byte) 0x74,(byte) 0x38,(byte) 0x75,(byte) 0x92,(byte) 0xB6,(byte) 0x3,(byte) 0x81,

            (byte) 0x56,(byte) 0x5,(byte) 0x2E,(byte) 0xCE, (byte) 0x4E,(byte) 0x9,(byte) 0x94,(byte) 0x1E,
            (byte) 0x76,(byte) 0xE8,(byte) 0xFD,(byte) 0xB1,(byte) 0x26,(byte) 0xBE,(byte) 0x38,(byte) 0x88,
            (byte) 0x37,(byte) 0xF9,(byte) 0x91,(byte) 0xDD,(byte) 0x3D,(byte) 0x42,(byte) 0x79,(byte) 0x11,
            (byte) 0x57,(byte) 0x2D,(byte) 0x38,(byte) 0x47, (byte) 0xB9,(byte) 0x53,(byte) 0x18,(byte) 0x93,


            (byte) 0x2E,(byte) 0xB9,(byte) 0x44,(byte) 0x3C,(byte) 0xCB,(byte) 0x61,(byte) 0x40,(byte) 0x8A,
            (byte) 0xEA,(byte) 0x6E,(byte) 0xFD,(byte) 0x0,(byte) 0x9C,(byte) 0x77,(byte) 0x46,(byte) 0xB5,
            (byte) 0xBF,(byte) 0xC1,(byte) 0x5,(byte) 0xAC, (byte) 0xFE,(byte) 0x52,(byte) 0xB,(byte) 0x78,
            (byte) 0xA6,(byte) 0x31,(byte) 0xBA,(byte) 0x16,(byte) 0x62,(byte) 0xF8,(byte) 0xBD,(byte) 0x9C,

            (byte) 0xB,(byte) 0x77,(byte) 0x12,(byte) 0xC5,(byte) 0xE1,(byte) 0x80,(byte) 0x9B,(byte) 0x3E,
            (byte) 0xAC,(byte) 0xDC,(byte) 0x0,(byte) 0x73, (byte) 0xC4,(byte) 0xBD,(byte) 0xA0,(byte) 0x76,
            (byte) 0x2E,(byte) 0x16,(byte) 0xEF,(byte) 0xC4,(byte) 0x95,(byte) 0x76,(byte) 0xF5,(byte) 0x77,
            (byte) 0x23,(byte) 0x8B,(byte) 0x38,(byte) 0x1B,(byte) 0x95,(byte) 0x76,(byte) 0xF5,(byte) 0x77,
    };

    List<Integer> sampleData = List.of(
            257, -2003, 2933, -334, 6660, 10754, -3086, 17067, 16589, -30536, -27184, 25558, 16256, -1349, 9128, -27466, 21517, 12580,
            7245, -5120, -18410, -13649, -5352, -7224, -24838, 4820, -20503, 2667, -12886, -650, 22818, 9512, 1943, 30808, -22238, -2252, -32475, 28267,
            23021, -17566, 16283, 27959, -8481, -17566, -3313, 31395, -8412, 23879, -21917, 1577, 32132, -31392, 16847, 30788, 18050, -6026, -20194, 12897,
            -23668, -10287, 17377, 29638, -10986, -30185, 23205, 13906, -6492, 17047, 31402, -28807, -11107, -9338, 1430, -9783, 6145, 29932, -20716, -28693,
            28564, 9562, 9836, 53, 26145, -10271, 2542, 25417, 21180, -24272, 21271, -12424, -1701, -16587, 17273, 4425, -12297, 9609, 8401, 27979,
            30290, 5250, 19264, 14311, 1815, -21796, 9838, -497, 8736, 7439, 10019, -11003, 315, 14307, 11660, 10958, 14126, -16261, -5929, 6365,
           -4051, -14610, 491, 3187, 17633, -27112, 17863, -24749, -16830, -31238, 21276, 7165, 20349, 13613, -30602, 24785, 11601, -4161, 13796, -7115,
            -18664, -3079, -7119, 7261, 17343, 14666, -16654, 14528, -27401, 12334, -27415, -18842, -5478, 5220, -14829, -2659, -14924, 3743, 23722, -28772,
            27346, -31440, -9717, -23370, -27480, 5718, -4762, 1898, -23347, 15619, -19855, 13818, 19268, 15365, 5857, -31916, -13294, -10811, -7418, -7972,
            -14682, -31012, -29790, 18777, -28371, 10534, 26355, -24434, -102, 26588, -24314, -15109, -7763, 5329, 2332, 25153, -32532, -21655, -31859, 13667,
            5818, 32253, -10124, 11763, -23142, 25728, 29708, 30008, -18798, -32509, 1366, -12754, 2382, 7828, -6026, -19971, -16858, -30664, -1737, -8815,
            16957, 4473, 11607, 18232, 21433, -27880, -18130, 15428, 25035, -30144, 28394, 253, 30620, -19130, -15937, -21499, 21246, 30731, 12710, 5818,
            -1950, -25411, 30475, -15086, -32543, 16027, -9044, 29440, -16956, 30368, 5678, -15121, 30357, 30709, -29917, 6968,30357, 30709
    );

    private byte[] secondPacketData = {
            (byte) 0x1,(byte) 0x0,(byte) 0x0,(byte) 0x0,
            (byte) 0x10,(byte) 0x0,(byte) 0x0,(byte) 0x0,
            (byte) 0x81,(byte) 0xCC,(byte) 0x81,(byte) 0x4D,
            (byte) 0x2E,(byte) 0xB9,(byte) 0x44,(byte) 0x3C,(byte) 0xCB,(byte) 0x61,(byte) 0x40,(byte) 0x8A,
            (byte) 0xEA,(byte) 0x6E,(byte) 0xFD,(byte) 0x0,(byte) 0x9C,(byte) 0x77,(byte) 0x46,(byte) 0xB5,
            (byte) 0xBF,(byte) 0xC1,(byte) 0x5,(byte) 0xAC, (byte) 0xFE,(byte) 0x52,(byte) 0xB,(byte) 0x78,
            (byte) 0xA6,(byte) 0x31,(byte) 0xBA,(byte) 0x16,(byte) 0x62,(byte) 0xF8,(byte) 0xBD,(byte) 0x9C,
    };

    
    @BeforeEach
    void setUp() {
        audioPacket = AudioPacket.of(packetData, 524);
        secondPacket = AudioPacket.of(secondPacketData, 44);
        uut = new AudioEvent();
    }

    @Test
    void testFirstAppend() {
        uut = uut.append(audioPacket);

        assertEquals(256, uut.getSampleCount());
        assertEquals((short)257, uut.getSamples().get(0));
        assertEquals((short)30709, uut.getSamples().get(255));
    }

    @Test
    void testSecondAppend() {
        uut = uut.append(audioPacket).append(secondPacket);
        // assertEquals( 1474, uut.getStartAt());
        assertEquals(256 +16, uut.getSampleCount());
        assertEquals((short)257, uut.getSamples().get(0));
        assertEquals((short)30731, uut.getSamples().get(267));
    }

    @Test
    void testToJson() throws JsonProcessingException, NoSuchAlgorithmException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        uut.append(audioPacket).append(secondPacket);
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(uut);
        System.out.println(json);
        AudioEvent fromJson = mapper.readValue(json, AudioEvent.class);
        assertEquals(fromJson.getSampleCount(), uut.getSampleCount());
       // assertEquals(fromJson.getStartAt(), uut.getStartAt());
        assertEquals(fromJson.getSamples().size(), uut.getSamples().size());
        int index = 0;
        for (Short s : uut.getSamples()) {
            assertEquals(fromJson.getSamples().get(index++), s);

        }

    }



}