package com.ifi.tp;

import com.ifi.tp.fights.bo.Fight;
import com.ifi.tp.fights.repository.FightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FightApi {

    public static void main(String... args){
        SpringApplication.run(FightApi.class, args);
    }

    @Bean
    @Autowired
    public CommandLineRunner demo(FightRepository fightRepository) {
        return (args) -> {

            var ashFight1 = new Fight();
            ashFight1.setNumber(1);
            ashFight1.setTrainer("Ash");
            ashFight1.setOpponent("Misty");
            ashFight1.setResult(true);

            var ashFight2 = new Fight();
            ashFight2.setNumber(1);
            ashFight2.setTrainer("Misty");
            ashFight2.setOpponent("Ash");
            ashFight2.setResult(false);

            fightRepository.save(ashFight1);
            fightRepository.save(ashFight2);
        };
    }
}
