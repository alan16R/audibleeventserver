package com.mechnicality.audibleeventserver.model.transformer;

public class BytesConvertor {
    // Use this to select the organization of
    // the incoming data including endianness
    public enum Width {
        Word32(4),
        Word16(2),
        Word64(8);

        private int byteCount;

        private Width(int bc) {
            this.byteCount = bc;
        }

        public int getByteCount() {
            return this.byteCount;
        }
    }

    private BytesConvertor() {}

    // the byte array must be at least 8 bytes long

    public static long longFromBytes(byte[] data) {
        return longFromBytes(data, 0, Width.Word32);
    }

    public static long longFromBytes(byte[] bytes, int offset, Width width) {
        if (bytes.length < 8) {
            throw new RuntimeException("Not enough bytes for an Instant");
        }
        long longValue = 0L;
        for (int i = 0; i < 8; i++) {
            longValue = longValue  | uByte2Long(bytes[i + offset]);
            longValue = Long.rotateRight(longValue, 8);
        }
        return longValue;
    }

    public static int intFromBytes(byte[] bytes, int offset, Width width) {
        if (bytes.length - offset < width.byteCount) {
            throw new RuntimeException("Not enough bytes for an int value");
        }
        int intValue = 0;
        for (int i = 0; i < width.byteCount; i++) {
            intValue = intValue  | uByte2Int(bytes[i + offset]);
            intValue = Integer.rotateRight(intValue, 8);
        }
        return intValue;
    }

    /**
     * This is necessary to convert two bytes that represent an
     * @param bytes
     * @param offset
     * @param width
     * @return
     */
    public static short shortFromBytes(byte[] bytes, int offset, Width width){
        if (bytes.length - offset < width.byteCount) {
            throw new RuntimeException("Not enough bytes for an int value");
        }
        int convertedBack = (int) bytes[offset];
        if (convertedBack < 0) {
            convertedBack = 256 + convertedBack;
        }
        convertedBack |= ((int) bytes[offset+1]) * 256;
        return Integer.valueOf(convertedBack).shortValue();
    }

    private static long uByte2Long(byte b) {
       return  b < 0 ? 256L + (long) b  : b;
    }

    private static int uByte2Int(byte b) {
        return  b < 0 ? 256 + (int) b  : b;
    }
}
