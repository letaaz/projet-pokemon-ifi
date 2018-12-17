package com.ifi.tp.service;

import com.ifi.tp.bo.HPNotification;
import com.ifi.tp.bo.Pokemon;
import com.ifi.tp.bo.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.jms.core.JmsTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

class NotificationServiceImplTest {

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @Mock
    private JmsTemplate jmsTemplate;

    @Captor
    private ArgumentCaptor<HPNotification> captor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void sendNotification_shouldCallTheJmsTemplate(){
        this.notificationService.sendNotification("test");

        verify(jmsTemplate).convertAndSend("notification", "test");
    }

    @Test
    void sendOneHPRecoveredNotification_shouldSendANotification() {
        var ash = new Trainer();
        var pikachu = new Pokemon();
        this.notificationService.sendOneHPRecoveredNotification(ash, pikachu);

        verify(jmsTemplate).convertAndSend(eq("notification"), captor.capture());

        var notif = captor.getValue();
        assertEquals(ash, notif.getTrainer());
        assertEquals(pikachu, notif.getPokemon());
        assertFalse(notif.isFullHP());
    }

    @Test
    void sendFullHPRecoveredNotification_shouldSendANotification() {
        var ash = new Trainer();
        var pikachu = new Pokemon();

        this.notificationService.sendFullHPRecoveredNotification(ash, pikachu);

        verify(jmsTemplate).convertAndSend(eq("notification"), captor.capture());

        var notif = captor.getValue();
        assertEquals(ash, notif.getTrainer());
        assertEquals(pikachu, notif.getPokemon());
        assertTrue(notif.isFullHP());
    }
}