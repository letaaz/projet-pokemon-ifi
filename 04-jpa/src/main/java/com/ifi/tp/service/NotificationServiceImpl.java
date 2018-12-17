package com.ifi.tp.service;

import com.ifi.tp.bo.HPNotification;
import com.ifi.tp.bo.Pokemon;
import com.ifi.tp.bo.Trainer;
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
    public void sendNotification(String message) {
        this.jmsTemplate.convertAndSend("notification", message);
    }

    @Override
    public void sendOneHPRecoveredNotification(Trainer trainer, Pokemon pokemon) {
        var hpNotification = new HPNotification();
        hpNotification.setTrainer(trainer);
        hpNotification.setPokemon(pokemon);
        hpNotification.setFullHP(false);

        this.jmsTemplate.convertAndSend("notification", hpNotification);
    }

    @Override
    public void sendFullHPRecoveredNotification(Trainer trainer, Pokemon pokemon) {
        var hpNotification = new HPNotification();
        hpNotification.setTrainer(trainer);
        hpNotification.setPokemon(pokemon);
        hpNotification.setFullHP(true);

        this.jmsTemplate.convertAndSend("notification", hpNotification);
    }
}
