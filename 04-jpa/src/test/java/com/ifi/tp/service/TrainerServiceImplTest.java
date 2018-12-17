package com.ifi.tp.service;

import com.ifi.tp.bo.Pokemon;
import com.ifi.tp.bo.Trainer;
import com.ifi.tp.repository.TrainerRepository;
import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceImplTest {

    @Mock
    TrainerRepository trainerRepository;

    @InjectMocks
    TrainerServiceImpl trainerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllTrainers_shouldCallTheRepository() {
        trainerService.getAllTrainers();

        verify(trainerRepository).findAll();
    }

    @Test
    void getTrainer_shouldCallTheRepository() {
        trainerService.getTrainer("Ash");

        verify(trainerRepository).findById("Ash");
    }

    @Test
    void createTrainer_shouldCallTheRepository() {
        var ash = new Trainer();
        trainerService.createTrainer(ash);

        verify(trainerRepository).save(ash);
    }

    @Test
    void updateTrainer_shouldCallTheRepository(){
        var ash = new Trainer();
        trainerService.updateTrainer(ash);

        verify(trainerRepository).save(ash);
    }

    @Test
    void deleteTrainer_shouldCallTheRepository(){
        trainerService.deleteTrainer("John");

        verify(trainerRepository).deleteById("John");
    }

    @Test
    void deleteTrainer_shouldDoNothingForAshAndMistyTheRepository(){
        trainerService.deleteTrainer("Ash");
        trainerService.deleteTrainer("Misty");

        verifyZeroInteractions(trainerRepository);
    }

    @Test
    void restoreOneHP_shouldAddOneHPToAllInjuredPokemons(){
        var misty = new Trainer();

        var staryu = new Pokemon();
        staryu.setPokemonNumber(120);
        staryu.setHp(30);
        var starmie = new Pokemon();
        starmie.setPokemonNumber(121);
        starmie.setHp(45);

        misty.setTeam(List.of(staryu, starmie));

        when(trainerRepository.findAll()).thenReturn(List.of(misty));

        var pokemonService = mock(PokemonService.class);
        when(pokemonService.getPokemonMaxHp(120)).thenReturn(30);
        when(pokemonService.getPokemonMaxHp(121)).thenReturn(60);
        trainerService.setPokemonService(pokemonService);

        trainerService.setNotificationService(mock(NotificationService.class));

        trainerService.restoreOneHp();

        verify(trainerRepository).findAll();
    }

    @Test
    void restoreOneHp_shouldBeAnnotated() throws NoSuchMethodException {
        var method = TrainerServiceImpl.class.getDeclaredMethod("restoreOneHp");
        var annotation = method.getAnnotation(Scheduled.class);

        assertNotNull(annotation);
        assertEquals(10000, annotation.fixedRate());
    }

    @Test
    void restoreOneHP_shouldShouldSendANotificationForFullHPRecovery(){
        var ash = new Trainer();

        var pikachu = new Pokemon();
        pikachu.setPokemonNumber(25);
        pikachu.setHp(34);

        ash.setTeam(List.of(pikachu));

        when(trainerRepository.findAll()).thenReturn(List.of(ash));

        var pokemonService = mock(PokemonService.class);
        when(pokemonService.getPokemonMaxHp(25)).thenReturn(35);
        trainerService.setPokemonService(pokemonService);

        var notificationService = mock(NotificationService.class);
        trainerService.setNotificationService(notificationService);

        trainerService.restoreOneHp();

        verify(notificationService).sendFullHPRecoveredNotification(ash, pikachu);
    }

    @Test
    void restoreOneHP_shouldShouldSendANotificationForOneHPRecovery(){
        var ash = new Trainer();

        var pikachu = new Pokemon();
        pikachu.setPokemonNumber(25);
        pikachu.setHp(33);

        ash.setTeam(List.of(pikachu));

        when(trainerRepository.findAll()).thenReturn(List.of(ash));

        var pokemonService = mock(PokemonService.class);
        when(pokemonService.getPokemonMaxHp(25)).thenReturn(35);
        trainerService.setPokemonService(pokemonService);

        var notificationService = mock(NotificationService.class);
        trainerService.setNotificationService(notificationService);

        trainerService.restoreOneHp();

        verify(notificationService).sendOneHPRecoveredNotification(ash, pikachu);
    }
}