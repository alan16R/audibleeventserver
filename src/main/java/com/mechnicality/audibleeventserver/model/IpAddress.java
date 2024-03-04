package com.mechnicality.audibleeventserver.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class IpAddress {

    private final List<Short> octets;
    private IpAddress(List<Short>octets) {
        this.octets = octets;
    }

    @Override
    public String toString() {
        return String.format("%d.%d.%d.%d", octets.get(0), octets.get(1), octets.get(2),octets.get(3));
    }

    public List<Short> getAsShort() {
        return Collections.unmodifiableList(octets);
    }

    public static IpAddress of( String ip) {
        String[] splits = ip.split("\\.");
        if (splits.length != 4) {
            throw new RuntimeException(ip + " is not a valid IPV4 IP address");
        }
        IpAddress found = new IpAddress(Arrays.stream(splits)
                .map(Short::valueOf)
                .toList());
        if(!found.validate()) {
            throw new RuntimeException(ip + " does not validate");
        }
        return found;
    }

    private boolean validate() {
        return !octets.stream().anyMatch(i ->i < 0 | i > 255);
    }

}
