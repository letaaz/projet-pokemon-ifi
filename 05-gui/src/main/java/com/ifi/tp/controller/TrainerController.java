package com.ifi.tp.controller;

import com.ifi.tp.fights.bo.FightNotification;
import com.ifi.tp.fights.service.FightService;
import com.ifi.tp.trainers.bo.Trainer;
import com.ifi.tp.trainers.service.TrainerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
public class TrainerController {

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private FightService fightService;

    @GetMapping("/trainers")
    ModelAndView index(){
        var modelAndView = new ModelAndView("trainers");

        modelAndView.addObject("trainers", trainerService.getAllTrainers());

        return modelAndView;
    }

    @GetMapping("/trainers/{name}")
    ModelAndView index(@PathVariable String name){
        var modelAndView = new ModelAndView("trainer");

        modelAndView.addObject("trainer", trainerService.getTrainer(name));

        return modelAndView;
    }

    @GetMapping("/trainers/{name}/fights")
    ModelAndView trainerFights(@PathVariable String name) {
        var modelAndView = new ModelAndView("fights");
        modelAndView.addObject("trainerName", name);
        modelAndView.addObject("fights", fightService.getAllFightsOfTrainer(name));
        return modelAndView;
    }

    @GetMapping("/trainers/{trainerName}/arena")
    ModelAndView trainersArena(@PathVariable String trainerName) {
        var modelAndView = new ModelAndView("arena");
        modelAndView.addObject("trainerName", trainerName);
        var trainers = new ArrayList(trainerService.getAllTrainers());
        trainers.removeIf((trainer) -> ((Trainer)trainer).getName().equals(trainerName));
        modelAndView.addObject("trainers", trainers);
        return modelAndView;
    }

    @GetMapping("/trainers/{trainerName}/fight/{opponentName}")
    ModelAndView startFight(@PathVariable String trainerName, @PathVariable String opponentName) {
        var modelAndView = new ModelAndView("fight_detail");
        // notifications = fightService.startFight(trainerName, opponentName).getNotifications();
        modelAndView.addObject("fight", new ArrayList<FightNotification>());
        return modelAndView;
    }
}
