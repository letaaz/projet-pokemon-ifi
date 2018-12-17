package com.ifi.tp;

import org.apache.activemq.broker.BrokerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrainerWebApp {

    public static void main(String... args) throws Exception{
        SpringApplication.run(TrainerWebApp.class, args);
    }

}
