package com.ifi.tp.listener;

import com.ifi.tp.fights.bo.FightNotification;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationReceiver {

    @JmsListener(destination="fightlog")
    public void receiveNotification(FightNotification notification){
        // Call fight service to retrieve current fights
        // Add received notificatins to current fight and save it ?
        System.out.println(notification.getRound() + " : " + notification.getMessage());
        // web socket
    }

}