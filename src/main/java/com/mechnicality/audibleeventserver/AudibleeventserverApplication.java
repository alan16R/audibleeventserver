package com.mechnicality.audibleeventserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AudibleeventserverApplication {

    public static void main(String[] args) {

       // SpringApplication.run(AudibleeventserverApplication.class, args);
        SpringApplication application = new SpringApplication(AudibleeventserverApplication.class);
        // ... customize application settings here
        application.run(args);
    }




}
