package com.ifi.tp.fights.service;

import com.ifi.tp.fights.bo.Fight;
import com.ifi.tp.fights.bo.FightNotification;
import com.ifi.tp.fights.repository.FightRepository;
import com.ifi.tp.pokemonTypes.bo.PokemonType;
import com.ifi.tp.pokemonTypes.service.PokemonService;
import com.ifi.tp.trainers.bo.Pokemon;
import com.ifi.tp.trainers.bo.Trainer;
import com.ifi.tp.trainers.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FightServiceImpl implements FightService {

    private FightRepository fightRepository;

    private TrainerService trainerService;
    private PokemonService pokemonService;
    private NotificationService notificationService;

    @Autowired
    public FightServiceImpl(FightRepository fightRepository) { this.fightRepository = fightRepository; }

    @Override
    public List<Fight> getAllFightsOfTrainer(String trainerName) {
        return this.fightRepository.findAllFightsOfTrainer(trainerName);
    }

    @Override
    public Fight updateFight(Fight fight){
        return this.fightRepository.save(fight);
    }

    @Override
    public Fight startFight(String trainerName, String opponentName) {
        var attackerTeam = trainerService.getTrainer(trainerName).getTeam();
        var attackerTeamIt = attackerTeam.iterator();
        var defenderTeam = trainerService.getTrainer(opponentName).getTeam();
        var defenderTeamIt = defenderTeam.iterator();

        // If one's trainer team is empty, handle exception
        if(attackerTeam.isEmpty() || defenderTeam.isEmpty())
            return null;

        // Determine 1st attacker with 2 pokemons
        var pokemons = determineAttacker(attackerTeamIt.next(), defenderTeamIt.next());
        var attacker = pokemons[0];
        var defender = pokemons[1];
        int round = 0;

        // while current defender's team is not dead || there is an attacker and a defender
        while (!trainerTeamIsDead(attackerTeam) && !trainerTeamIsDead(defenderTeam)) {
            while (defender.getHp() > 0) {
                round++;
                // attacker attack
                attack(round, attacker, defender);
                // if defender is dead
                if (defender.getHp() < 0)
                    break;
                // change turns
                attacker = changeRole(attacker, pokemons);
                defender = changeRole(defender, pokemons);
            }
            // defender's pokemon is dead, send notification
            //sendPokemonDeadNotification(defender);
            var previous = pokemonService.getPokemonType(defender.getPokemonNumber());
            if (defenderTeamIt.hasNext() && defenderTeam.contains(defender))  //dead pokemon was in defender's team
                defender = defenderTeamIt.next();
            else if (attackerTeamIt.hasNext()) // dead pokemon was in attacker's team
                defender = attackerTeamIt.next();
            else
                break;
            var next = pokemonService.getPokemonType(defender.getPokemonNumber());
            this.notificationService.sendFightEventNotification(round, previous.getName() + " is KO ! "
                    + next.getName() + " enters battle ! ");
            pokemons[1] = defender;
        }

        // determine winner
        var fight = new Fight();
        if (trainerTeamIsDead(defenderTeam)) { // trainer won
            fight.setTrainer(trainerName);
            fight.setOpponent(opponentName);
            fight.setResult(true);
            this.notificationService.sendFightEventNotification(round, pokemonService.getPokemonType(defender.getPokemonNumber()).getName() + " is KO ! "
                    + trainerName + " wins ! ");
            // send fight end notifications
            //sendFightEndNotifications(fight);
        } else { // trainer lost won
            fight.setTrainer(trainerName);
            fight.setOpponent(opponentName);
            fight.setResult(false);
            this.notificationService.sendFightEventNotification(round, pokemonService.getPokemonType(defender.getPokemonNumber()).getName() + " is KO ! "
                    + opponentName + " wins ! ");
            // send fight end notifications
            //sendFightEndNotifications(fight);
        }
        // save fight result
        trainerService.updateTrainer(trainerService.getTrainer(trainerName));
        trainerService.updateTrainer(trainerService.getTrainer(opponentName));
        fightRepository.save(fight);
        return fight;
    }

    private void sendFightEndNotifications(Fight fight) {
        // TO DO
    }

    private void sendPokemonDeadNotification(Pokemon pokemon) {
        // TO DO
    }

    private boolean trainerTeamIsDead(List<Pokemon> team){
        for (Pokemon pokemon: team) {
            if (pokemon.getHp() > 0)
                return false;
        }
        return true;
    }

    private Pokemon changeRole(Pokemon current, Pokemon[] pokemons){
        if (current.equals(pokemons[0]))
            return pokemons[1];
        else
            return pokemons[0];
    }

    /**
     * Decrement the defender hp accordingly to attacker's attack damage
     * @param attacker
     * @param defender
     */
    private void attack(int round, Pokemon attacker, Pokemon defender){
        var attackerPokemonType = pokemonService.getPokemonType(attacker.getPokemonNumber());
        var defenderPokemonType = pokemonService.getPokemonType(defender.getPokemonNumber());
        var defense = defenderPokemonType.getStats().getDefense()  + defender.getLevel();
        var attackPower = attackerPokemonType.getStats().getAttack() + attacker.getLevel() - defense;
        var damage = attackPower <= 0 ? 1 : attackPower;

        this.notificationService.sendFightEventNotification(round, "" + attackerPokemonType.getName() + " hits " + defenderPokemonType.getName() + "! "
                + defenderPokemonType.getName() + " loses " + damage + " HP.");
        defender.setHp(defender.getHp() - damage);
    }

    /**
     * Returns an array where 1st element is the quickest pokemon, therefore the one to attack first
     * @param pokemon1
     * @param pokemon2
     * @return
     */
    private Pokemon[] determineAttacker(Pokemon pokemon1, Pokemon pokemon2) {
        var pokemonType1 = pokemonService.getPokemonType(pokemon1.getPokemonNumber());
        var pokemonType2 = pokemonService.getPokemonType(pokemon2.getPokemonNumber());
        if (pokemonType1.getStats().getSpeed() + pokemon1.getLevel() >= pokemonType2.getStats().getSpeed() + pokemon2.getLevel()) {
            System.out.println("" + pokemonType1.getName() + " is faster ! He starts");
            return new Pokemon[]{pokemon1, pokemon2};
        } else {
            System.out.println("" + pokemonType2.getName() + " is faster ! He starts");
            return new Pokemon[]{pokemon2, pokemon1};
        }
    }

    @Autowired
    public void setPokemonService(PokemonService pokemonService) { this.pokemonService = pokemonService; }

    @Autowired
    public void setTrainerService(TrainerService trainerService) { this.trainerService = trainerService; }

    @Autowired
    public void setNotificationService(NotificationService notificationService) { this.notificationService = notificationService; }

}
