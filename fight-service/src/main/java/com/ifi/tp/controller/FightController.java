package com.ifi.tp.controller;

import com.ifi.tp.fights.bo.Fight;
import com.ifi.tp.fights.service.FightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
public class FightController {

    private FightService fightService;

    @Autowired
    public FightController(FightService fightService) {
        this.fightService = fightService;
    }

    @GetMapping(value = "/")
    @ResponseBody
    String index(){
        return "Fight service";
    }

    @GetMapping(value = "/fights/{name}", produces = "application/json")
    List<Fight> trainerFights(@PathVariable String name) {
        return this.fightService.getAllFightsOfTrainer(name);
    }

    @PostMapping(value = "/fights/{trainerName}/{opponentName}", produces = "application/json")
    Fight resultFightBetween(@PathVariable String trainerName, @PathVariable String opponentName) {
        var modelAndView = new ModelAndView("fight");
        return this.fightService.startFight(trainerName, opponentName);

    }
}
