package com.ifi.tp.fights.service;

import com.ifi.tp.fights.bo.FightNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    private JmsTemplate jmsTemplate;

    @Autowired
    public NotificationServiceImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void sendFightEventNotification(int round, String message) {
        var fightNotification = new FightNotification();
        fightNotification.setRound(round);
        fightNotification.setMessage(message);
        this.jmsTemplate.convertAndSend("fightlog", fightNotification);
    }

    @Override
    public void sendDeadEventNotification(int turn, String message, String pokemonName) {

    }
}
