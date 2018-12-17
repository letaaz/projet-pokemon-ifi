package com.ifi.tp.service;

import com.ifi.tp.TrainerApi;
import com.ifi.tp.config.MessagingConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {TrainerApi.class})
class NotificationServiceImplIntegrationTest {

    @Test
    void sendNotification(@Autowired NotificationService notificationService) {
        notificationService.sendNotification("test !");
    }
}