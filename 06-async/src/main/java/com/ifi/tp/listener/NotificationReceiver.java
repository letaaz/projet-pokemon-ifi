package com.ifi.tp.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationReceiver {


    @JmsListener(destination="notification")
    public void receiveNotification(String notification){
        System.out.println(notification);
    }

}
