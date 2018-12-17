package com.ifi.tp.fights.service;

import com.ifi.tp.fights.bo.Fight;

import java.util.List;

public interface FightService {

    List<Fight> getAllFightsOfTrainer(String trainerName);

    Fight startFight(String trainerName, String opponentName);
}
