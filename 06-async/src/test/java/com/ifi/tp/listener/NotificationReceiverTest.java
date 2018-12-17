package com.ifi.tp.listener;

import org.junit.jupiter.api.Test;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static org.junit.jupiter.api.Assertions.*;

class NotificationReceiverTest {

    @Test
    void notificationReceiver_shouldBeAnnotated(){
        var componentAnnotation = NotificationReceiver.class.getAnnotation(Component.class);
        assertNotNull(componentAnnotation);
    }

    @Test
    void receiveNotification_shouldBeAnnotated() throws NoSuchMethodException {
        var receiveNotificationMethod = NotificationReceiver.class.getDeclaredMethod("receiveNotification", String.class);
        var jmsListenerAnnotation = receiveNotificationMethod.getAnnotation(JmsListener.class);
        assertNotNull(jmsListenerAnnotation);
        assertEquals("notification", jmsListenerAnnotation.destination());
    }

}