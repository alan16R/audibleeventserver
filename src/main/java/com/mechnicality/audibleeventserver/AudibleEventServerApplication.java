package com.mechnicality.audibleeventserver;

import com.mechnicality.audibleeventserver.model.PacketType;
import com.mechnicality.audibleeventserver.model.packet.ControlPacket;
import com.mechnicality.audibleeventserver.service.TaskRunner;
import com.mechnicality.audibleeventserver.service.UdpSenderService;
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
     @Autowired
     private UdpSenderService udpSenderService;

    private static final Logger logger = LoggerFactory.getLogger(AudibleEventServerApplication.class);

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(AudibleEventServerApplication.class);
        // ... customize application settings here
        application.run(args);
    }

     @Override
     public void run(ApplicationArguments args) throws Exception {
         logger.info("Sending wakeup packet to module.");
         udpSenderService.sendPacket(ControlPacket.of(b -> b
                 .type(PacketType.Wakeup)  // wakeup packets get no acknowlegement.
         ));
         taskRunner.onStartup();

     }
 }
