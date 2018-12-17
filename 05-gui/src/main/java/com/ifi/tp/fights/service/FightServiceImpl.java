package com.ifi.tp.fights.service;

import com.ifi.tp.fights.bo.Fight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class FightServiceImpl implements FightService {

    private RestTemplate restTemplate;
    private String fightServiceUrl;

    @Override
    public List<Fight> getAllFightsOfTrainer(String trainerName) {
        var url = fightServiceUrl + "/fights/{name}";
        var fights = restTemplate.getForObject(url, Fight[].class, trainerName);
        return Arrays.asList(fights);
    }

    @Override
    public Fight startFight(String trainerName, String opponentName) {
        var url = fightServiceUrl + "/fights/{trainerName}/{opponentName}";
        return restTemplate.getForObject(url, Fight.class, trainerName, opponentName);
    }


    @Autowired
    void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Value("${fight.service.url}")
    public void setFightServiceUrl(String fightServiceUrl) { this.fightServiceUrl = fightServiceUrl; }
}
