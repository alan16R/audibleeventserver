package com.mechnicality.audibleeventserver.model;

import java.time.Instant;
import java.util.function.Consumer;

public class InfoBuilder {
    public  IpAddress sender;
    public  boolean connected;
    public  boolean enabled;
    public  short onThreshold;
    public  short offThreshold;
    public  Instant lastConnected;

    public InfoBuilder with(Consumer<InfoBuilder> builderFunction) {
        builderFunction.accept(this);
        return this;
    }

    public Info createInfo() {
        return new Info(sender, connected, enabled, onThreshold, offThreshold, lastConnected);
    }

}
