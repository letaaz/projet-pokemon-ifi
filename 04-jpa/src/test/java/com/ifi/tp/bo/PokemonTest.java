package com.ifi.tp.bo;

import org.junit.jupiter.api.Test;

import javax.persistence.*;

import static org.junit.jupiter.api.Assertions.*;

class PokemonTest {

    @Test
    void pokemon_shouldBeAnEntity(){
        assertNotNull(Pokemon.class.getAnnotation(Entity.class));
    }

    @Test
    void pokemonId_shouldBeAGeneratedId() throws NoSuchFieldException {
        assertNotNull(Pokemon.class.getDeclaredField("id").getAnnotation(Id.class));
        assertNotNull(Pokemon.class.getDeclaredField("id").getAnnotation(GeneratedValue.class));
    }

}