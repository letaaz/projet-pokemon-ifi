package com.ifi.tp.service;

import com.ifi.tp.bo.Trainer;
import com.ifi.tp.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {

    private TrainerRepository trainerRepository;

    private List<String> protectedTrainers = List.of("Ash","Misty");

    @Autowired
    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public Iterable<Trainer> getAllTrainers() {
        return this.trainerRepository.findAll();
    }

    @Override
    public Trainer getTrainer(String name) {
        return this.trainerRepository.findById(name).orElse(null);
    }

    @Override
    public Trainer createTrainer(Trainer trainer) {
        return this.trainerRepository.save(trainer);
    }

    @Override
    public Trainer updateTrainer(Trainer trainer) {
        return this.trainerRepository.save(trainer);
    }

    @Override
    public void deleteTrainer(String trainerName) {
        if(protectedTrainers.contains(trainerName)){
            return;
        }
        this.trainerRepository.deleteById(trainerName);
    }


    private NotificationService notificationService;

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    private PokemonService pokemonService;

    @Autowired
    public void setPokemonService(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @Scheduled(fixedRate = 60000)
    void restoreOneHp(){
        var trainers = this.getAllTrainers();
        for(var trainer : trainers){
            trainer.getTeam().stream().forEach(pokemon -> {
                var maxHp = pokemonService.getPokemonMaxHp(pokemon.getPokemonNumber());
                if(pokemon.getHp() < maxHp){
                    pokemon.setHp(pokemon.getHp() + 1);
                    this.updateTrainer(trainer);
                    if(pokemon.getHp() == maxHp){
                        this.notificationService.sendFullHPRecoveredNotification(trainer, pokemon);
                    }
                    else{
                        this.notificationService.sendOneHPRecoveredNotification(trainer, pokemon);
                    }
                }
            });
        }
    }

}
