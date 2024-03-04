package com.mechnicality.audibleeventserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mechnicality.audibleeventserver.model.packet.AudioPacket;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AudioEvent {

    private static final Logger logger = LoggerFactory.getLogger(AudioEvent.class);
    // assume offset in original packet is always 0.

    @JsonIgnore
    private boolean empty;

    @JsonIgnore
    private boolean finishFound;

    // 8 bytes
    private Instant timestamp;
    // 4 bytes
    private int sampleCount;
    // initially 'shorts' of 2 bytes
    private List<Short> samples;

    public AudioEvent() {
        this.empty = true;
        this.samples = new ArrayList<>();
        this.finishFound = false;
    }



    @NotNull public AudioEvent append(AudioPacket audioPacket) {
        if(this.empty) {
            if (audioPacket.getPacketType() != PacketType.Start) {
                logger.warn("Missed packet start!");
            }
            this.reset();
            empty = false;
        } else if (audioPacket.getPacketType() == PacketType.Finish) {
                this.finishFound = true;
        }

        int packetSamples = audioPacket.getSize();

//        for (int j = 0; j < packetSamples; j++) {
//            short sampleValue = BytesConvertor.shortFromBytes(audioPacket.getValue(), j*2, BytesConvertor.Width.Word16);
            samples.addAll(audioPacket.getValue());
//        }
        this.sampleCount  += packetSamples; // number of SAMPLES
        return this;
    }


    @Override
    public String toString() {
        return "AudioEvent:[ @" + timestamp + ", sampleCount=" + sampleCount + " ]";
    }

    @JsonIgnore
    public boolean isFinished() {
        boolean limitExceeded = this.sampleCount >= 32000;
        return this.finishFound || limitExceeded;
    }

    public void reset() {
        timestamp = Instant.now();
        samples = new ArrayList<>();
        sampleCount = 0;
        empty = true;
        finishFound = false;
    }
}
