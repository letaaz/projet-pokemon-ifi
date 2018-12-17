package com.ifi.tp.service;

import com.ifi.tp.bo.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class PokemonServiceImpl implements PokemonService {

    private String pokemonServiceUrl;

    private RestTemplate restTemplate;

    @Value("${pokemon.service.url}")
    public void setPokemonServiceUrl(String pokemonServiceUrl) {
        this.pokemonServiceUrl = pokemonServiceUrl;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public int getPokemonMaxHp(int pokemonId) {
        var pokemonType = restTemplate.getForObject(pokemonServiceUrl + "/pokemons/" + pokemonId, Map.class);
        var stats = (Map)pokemonType.get("stats");
        return (Integer)stats.get("hp");
    }
}
