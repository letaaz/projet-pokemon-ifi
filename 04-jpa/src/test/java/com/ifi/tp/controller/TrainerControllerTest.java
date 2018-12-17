package com.ifi.tp.controller;

import com.ifi.tp.bo.Trainer;
import com.ifi.tp.service.TrainerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class TrainerControllerTest {

    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllTrainers_shouldCallTheService() {
        trainerController.getAllTrainers();

        verify(trainerService).getAllTrainers();
    }

    @Test
    void getTrainer_shouldCallTheService() {
        trainerController.getTrainer("Ash");

        verify(trainerService).getTrainer("Ash");
    }

    @Test
    void createTrainer_shouldCallTheService() {
        var ash = new Trainer("Ash");

        trainerController.createTrainer(ash);

        verify(trainerService).createTrainer(ash);
    }

    @Test
    void updateTrainer_shouldCallTheService(){
        var ash = new Trainer("Ash");

        trainerController.updateTrainer(ash);

        verify(trainerService).updateTrainer(ash);
    }

    @Test
    void deleteTrainer_shouldCallTheService(){
        trainerController.deleteTrainer("Garry");
        verify(trainerService).deleteTrainer("Garry");
    }

    @Test
    void shouldBeAnnotated() throws NoSuchMethodException {
        var annotation = TrainerController.class.getAnnotation(RestController.class);
        assertNotNull(annotation);

        var globalMapping = TrainerController.class.getAnnotation(RequestMapping.class);
        assertNotNull(globalMapping);
        assertArrayEquals(new String[]{"/trainers"}, globalMapping.value());
    }
}