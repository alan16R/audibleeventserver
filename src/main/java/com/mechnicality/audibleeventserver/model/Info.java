package com.mechnicality.audibleeventserver.model;

import java.time.Instant;

public class Info {
    public final IpAddress sender;
    public final Instant lastConnected;
    public final boolean connected;
    public final boolean enabled;
    public final short onThreshold;
    public final short offThreshold;

    public Info(IpAddress sender, boolean connected, boolean enabled, short onThreshold, short offThreshold, Instant lastConnected) {
        this.sender = sender;
        this.connected = connected;
        this.enabled = enabled;
        this.onThreshold = onThreshold;
        this.offThreshold = offThreshold;
        this.lastConnected = lastConnected;
    }
}
