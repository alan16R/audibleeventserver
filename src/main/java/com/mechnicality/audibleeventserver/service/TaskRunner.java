package com.mechnicality.audibleeventserver.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TaskRunner {

    private static final Logger logger = LoggerFactory.getLogger(TaskRunner.class);

    private final QueueManager queueManager;

    private final UdpListenerService listenerService;

    public TaskRunner(QueueManager queueManager, UdpListenerService listenerService) {
        this.listenerService = listenerService;
        this.queueManager = queueManager;
    }

    @PostConstruct
    public void onStartup() {
        logger.info("Starting TaskRunner - 2 second delay");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Should never happen?");
        }
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            List<Future<String>> futures = executorService.invokeAll(List.of(queueManager,listenerService));
            while(true) {
                for (Future<String> f: futures) {
                    logger.info(f.get());  // just hangs until something happens
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }
}
