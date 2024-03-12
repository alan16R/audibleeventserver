package com.mechnicality.audibleeventserver.model;

import java.util.Arrays;
import java.util.Optional;

public enum PacketType {

    Start (0),
    Sequence(1),

    Error(0xe),
    Finish(0xf),
    SetGain(2),
    SetLimit(3),
    GetInfo(4),
    Info(5),
    // special packet used to tell the remote host that we are ready.
    Wakeup(6), Command(7);



    private final int code;

    private PacketType(int code) {
        this.code = code;
    }

    public static Optional<PacketType> getFromCode(int c){
        return Arrays.stream(PacketType.values()).filter(p -> p.code == c).findFirst();
    }


}
