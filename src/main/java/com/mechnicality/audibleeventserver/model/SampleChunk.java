package com.mechnicality.audibleeventserver.model;

import com.mechnicality.audibleeventserver.model.transformer.BytesConvertor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a chunk of samples that are captured from one UDP packet.
 *
 * They are reassembled by the QueueManager and then saved to disk.
 *
 */

@Getter
public class SampleChunk {

    private final List<Short> samples;
    private SampleChunk(List<Short> samples) {
        this.samples = samples;
    }



    public boolean isEmpty() {
        return this.samples.isEmpty();
    }

    public boolean isFull() {
        return this.samples.size() > 16000; // hardcode for moment.
    }

    public static SampleChunk  of(byte[] data) {
        int offset = 8; // 4 bytes for marker, 4 bytes for sample count
        int sampleCount = BytesConvertor.intFromBytes(data,4, BytesConvertor.Width.Word32);
        List<Short> samples = new ArrayList<>();
        for (int i = 0; i < sampleCount; i++) {
            Short s = BytesConvertor.shortFromBytes(data, offset + i * 2, BytesConvertor.Width.Word16);
            samples.add(s);
        }
        return new SampleChunk(samples);
    }

    public int accumulate(SampleChunk toAdd) {
        this.samples.addAll(toAdd.samples);
        return this.samples.size();
    }

    public  SampleChunk append(SampleChunk appended) {
        accumulate(appended);
        return this;
    }

    public static  SampleChunk newInstance() {
        return new SampleChunk(new ArrayList<>());
    }


    public void reset() {
        this.samples.clear();
    }
}