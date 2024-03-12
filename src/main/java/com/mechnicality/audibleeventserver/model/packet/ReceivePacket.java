package com.mechnicality.audibleeventserver.model.packet;

import com.mechnicality.audibleeventserver.model.PacketType;

public interface ReceivePacket<T> {

    /**
     * Low order bits are the PacketType
     * @return
     */
    PacketType getPacketType();

    /**
     * Count of data elements (may be 0)
     * as the type of data (ie 5 shorts)
     */
    int getSize();
    /**
     * Number in logical sequence.
     * Starts at 0 and ends well before
     * MAX_INT!
     */
    int getSequence();


    byte[] getBytes();
}
