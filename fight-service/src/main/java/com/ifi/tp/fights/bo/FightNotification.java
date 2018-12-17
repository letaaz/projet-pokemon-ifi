package com.ifi.tp.fights.bo;

public class FightNotification {

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int round;
    private String message;
}
