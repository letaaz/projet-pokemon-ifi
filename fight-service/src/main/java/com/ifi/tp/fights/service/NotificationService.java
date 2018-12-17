package com.ifi.tp.fights.service;

public interface NotificationService {

    void sendFightEventNotification(int turn, String message);

    void sendDeadEventNotification(int turn, String message, String pokemonName);
}