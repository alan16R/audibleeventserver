package com.mechnicality.audibleeventserver.service;

import com.mechnicality.audibleeventserver.model.AudioEvent;
import com.mechnicality.audibleeventserver.model.packet.AudioPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.Callable;

public class QueueManager implements Callable<String> {

    private static final Logger logger = LoggerFactory.getLogger(QueueManager.class);
    private final AudioEventWriter eventWriter;

    private final Queue<AudioPacket> eventQueue;

    private boolean running;

    public QueueManager(AudioEventWriter writer, Queue<AudioPacket> eventQueue) {
        this.eventQueue = eventQueue;
        this.eventWriter = writer;
    }

    public void start() {
        this.running = true;
    }

    public void stop() {
        this.running = false;
        logger.info("BufferManager stopped");
    }
    @Override
    public String call() throws Exception {
        try {
            AudioEvent event = new AudioEvent();
            logger.info("Starting buffer listener");
            start();
            while (running) {
                if (eventQueue.peek() != null) {
                    event.append(eventQueue.remove());
                    if(event.isFinished()) {
                        eventWriter.writeEvent(event);
                        event.reset();
                    }
                    logger.info("Event size is {}", event.getSampleCount());
                }
                Thread.yield();
            }
        } catch (Exception e) {
            logger.error("QueueManager run() encountered an error: {}, shutting down!", e.getMessage());
            this.stop();
        }
        return "QueueManager finished";
    }

}

