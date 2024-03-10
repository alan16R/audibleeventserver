package com.mechnicality.audibleeventserver.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mechnicality.audibleeventserver.model.AudioEvent;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AudioEventWriter {

    private static final Logger logger = LoggerFactory.getLogger(AudioEventWriter.class);

    private final ObjectMapper objectMapper;
    private final DateTimeFormatter simpleDateFormat;

    private final String eventsPath;
    public AudioEventWriter(String eventsPath) {
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        this.eventsPath = eventsPath;
        this.simpleDateFormat = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        logger.info("AudioEventWriter will save files to {}", eventsPath);
    }
    @PostConstruct
    public void init() {
        logger.info("Creating AudioEventWriter");
        Path eventPath = Path.of(eventsPath);
        if(Files.notExists(eventPath)) {
            try {
                Files.createDirectories(eventPath);
                logger.info("Creating directories for {}", eventPath);
            } catch (IOException e) {
                logger.warn("Failed to create directories for {}", eventPath);
            }
        }
    }

    public String writeEvent(AudioEvent event) {
        long milliSeconds = System.currentTimeMillis() % (24L * 3600);

        Path eventPath = Path.of(eventsPath,"event-" + simpleDateFormat.format(LocalDateTime.now()) + ".json" );

        try {
            String json = objectMapper.writeValueAsString(event);
            Files.writeString(eventPath, json, StandardOpenOption.CREATE);
            logger.info("Saved {} to: {}", event, eventPath.getFileName());
        } catch (IOException e) {
            logger.warn("Failed to write event with sample time of: {} - {}", event.getTimestamp(),e.getMessage());
        }
        return "FAILED TO WRITE EVENT";
    }


}
