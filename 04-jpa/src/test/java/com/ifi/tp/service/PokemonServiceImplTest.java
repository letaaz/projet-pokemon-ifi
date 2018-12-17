package com.ifi.tp.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PokemonServiceImplTest {

    @Test
    void getPokemonBaseHP_shouldReturnBaseHPStat(){
        var pokemonServiceUrl = "https://ifi-pokemon-api.herokuapp.com";
        var restTemplate = mock(RestTemplate.class);

        var pokemonService = new PokemonServiceImpl();
        pokemonService.setPokemonServiceUrl(pokemonServiceUrl);
        pokemonService.setRestTemplate(restTemplate);

        var expectedUrl = pokemonServiceUrl + "/pokemons/25";
        when(restTemplate.getForObject(expectedUrl, Map.class)).thenReturn(Map.of("stats", Map.of("hp", 35)));

        var pikachuBaseHp = pokemonService.getPokemonMaxHp(25);
        assertEquals(35, pikachuBaseHp);

        verify(restTemplate).getForObject(expectedUrl, Map.class);
    }

    @Test
    void pokemonServiceImpl_shouldBeAnnotated() throws NoSuchMethodException {
        var serviceAnnotation = PokemonServiceImpl.class.getAnnotation(Service.class);
        assertNotNull(serviceAnnotation);

        var setPokemonServiceUrlMethod = PokemonServiceImpl.class.getDeclaredMethod("setPokemonServiceUrl", String.class);
        var valueAnnotation = setPokemonServiceUrlMethod.getAnnotation(Value.class);
        assertNotNull(valueAnnotation);
        assertEquals("${pokemon.service.url}", valueAnnotation.value());
    }

}