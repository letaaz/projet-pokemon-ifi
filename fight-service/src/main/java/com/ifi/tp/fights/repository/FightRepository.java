package com.ifi.tp.fights.repository;

import com.ifi.tp.fights.bo.Fight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FightRepository extends JpaRepository<Fight, Integer> {

    @Query("SELECT f FROM Fight f WHERE f.trainerName = ?1")
    List<Fight> findAllFightsOfTrainer(String trainerName);

}
