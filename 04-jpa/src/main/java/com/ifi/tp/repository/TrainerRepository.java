package com.ifi.tp.repository;

import com.ifi.tp.bo.Trainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends CrudRepository<Trainer, String> {

}
