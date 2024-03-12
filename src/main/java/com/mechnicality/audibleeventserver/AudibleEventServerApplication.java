package com.mechnicality.audibleeventserver;

import com.mechnicality.audibleeventserver.service.TaskRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AudibleEventServerApplication  implements ApplicationRunner
 {
     @Autowired
     private TaskRunner taskRunner;


    private static final Logger logger = LoggerFactory.getLogger(AudibleEventServerApplication.class);

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(AudibleEventServerApplication.class);
        // ... customize application settings here
        application.run(args);
    }

     @Override
     public void run(ApplicationArguments args) throws Exception {
         System.out.println("Got here");
         taskRunner.onStartup();
     }
 }
