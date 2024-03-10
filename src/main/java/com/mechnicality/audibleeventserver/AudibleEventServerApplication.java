package com.mechnicality.audibleeventserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AudibleEventServerApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(AudibleEventServerApplication.class);

    public static void main(String[] args) {

        SpringApplication.run(AudibleEventServerApplication.class, args);
//        SpringApplication application = new SpringApplication(AudibleEventServerApplication.class);
//        // ... customize application settings here
//        application.run(args);
    }


    @Override
    public void run(String... args) throws Exception {
        logger.info("Running commands");
    }
}
