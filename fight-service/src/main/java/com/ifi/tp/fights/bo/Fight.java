package com.ifi.tp.fights.bo;

import com.ifi.tp.trainers.bo.Trainer;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Fight {

    @Id
    @GeneratedValue

    private int number;
    private String trainerName;
    private String opponentName;
    private boolean result; // 0 if trainer lost, 1 otherwise

    public Fight() {}

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getOpponent() {
        return opponentName;
    }

    public void setOpponent(String opponentName) {
        this.opponentName = opponentName;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getTrainer() { return trainerName; }

    public void setTrainer(String trainerName) { this.trainerName = trainerName; }

}
