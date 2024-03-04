package com.mechnicality.audibleeventserver.model.transformer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BytesConvertorTest {
    @BeforeEach
    void setUp() {
    }

    @Test
    void longFromBytes() {
        long initial = 1708270125721L;
        Instant instant = Instant.ofEpochMilli(initial);
        long instantAsLong = instant.toEpochMilli();
        System.out.printf("%016x",instantAsLong);
        // 0000018dbcd58299
        byte [] bytes = { (byte) 0x99, (byte) 0x82, (byte) 0xd5, (byte) 0xbc, (byte) 0x8d, 1, 0, 0 };
        Instant converted = Instant.ofEpochMilli(BytesConvertor.longFromBytes(bytes,0, BytesConvertor.Width.Word32));
        System.out.printf("%016x%n",converted.toEpochMilli());
        assertEquals(converted, instant);
    }

    @Test
    void testArrayOfShortsFromBytes() {
        List<Short> shortsList = List.of(
                Integer.valueOf(1).shortValue(),
                Integer.valueOf(-2).shortValue(),
                Integer.valueOf( 100).shortValue(),
                Integer.valueOf(-200).shortValue(),
                Integer.valueOf(1000).shortValue(),
                Integer.valueOf(-2000).shortValue(),
                Integer.valueOf(10000).shortValue(),
                Integer.valueOf(-20000).shortValue(),
                Integer.valueOf(30000).shortValue(),
                Integer.valueOf(-30000).shortValue()
        );
        // convert those to a byte array

        byte[] bytes = new byte[shortsList.size()* BytesConvertor.Width.Word16.getByteCount()];
        int offset = 0;
        for (Short i: shortsList) {
            for (int j = 0; j < BytesConvertor.Width.Word16.getByteCount()/2; j++) {
                Integer sval = Short.toUnsignedInt(i);
                bytes[offset++] = sval.byteValue();
                bytes[offset++] = Integer.valueOf(sval / 256).byteValue();
            }
        }
       List<Short> results = new ArrayList<>();
       int comparisonIndex = 0;
       for( ; comparisonIndex < bytes.length/BytesConvertor.Width.Word16.getByteCount(); comparisonIndex++) {
           Short result = BytesConvertor.shortFromBytes(bytes,comparisonIndex*BytesConvertor.Width.Word16.getByteCount(), BytesConvertor.Width.Word16);
           results.add(result);
       }
       for (comparisonIndex =0; comparisonIndex < results.size(); comparisonIndex++){
           assertEquals(shortsList.get(comparisonIndex),results.get(comparisonIndex), " Failed at " + comparisonIndex);
       }
    }

    // 200 = 128 + 64  + 8 = 0110,0100
    @Test
    void testShortValues() {
        List<String> values = List.of(
                "1",
                "-1",
                "-2",
                "10",
                "-10",
                "100",
                "-100",
                "1000",
                "-1000",
                "10000",
                "-10000",
                "20000",
                "-20000"
        );
        for (String val : values) {
            Short s200 = Short.valueOf(val);
            System.out.printf("%x\n", s200);
            String valHex = String.format("%x", s200);
            byte[] bytes = new byte[2];
            Integer sval = Short.toUnsignedInt(s200);
            bytes[0] = sval.byteValue();
            bytes[1] = Integer.valueOf(sval / 256).byteValue();
            Short cvs = BytesConvertor.shortFromBytes(bytes,0, BytesConvertor.Width.Word16);
            System.out.printf("%x -- %d\n", cvs,cvs);
            assertEquals(valHex, String.format("%x",cvs) );
        }
    }

    @Test
    void testSampleM13184() {
        byte[] bytes = { (byte) 0x80, (byte) 0xCC};
        short negValue = BytesConvertor.shortFromBytes(bytes, 0, BytesConvertor.Width.Word16);
        assertEquals( (int) negValue, -13184 );
    }

}