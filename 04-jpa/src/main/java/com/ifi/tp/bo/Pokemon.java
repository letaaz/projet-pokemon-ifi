package com.ifi.tp.bo;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Pokemon {

    @Id
    @GeneratedValue
    private int id;

    private int pokemonNumber;

    private int level;

    private int hp;

    public Pokemon() {
    }

    public Pokemon(int pokemonId, int level) {
        this.pokemonNumber = pokemonId;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPokemonNumber() {
        return pokemonNumber;
    }

    public void setPokemonNumber(int pokemonNumber) {
        this.pokemonNumber = pokemonNumber;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
