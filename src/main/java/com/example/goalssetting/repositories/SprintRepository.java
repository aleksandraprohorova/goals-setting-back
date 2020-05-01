package com.example.goalssetting.repositories;

import com.example.goalssetting.entity.Sprint;
import org.springframework.data.repository.CrudRepository;

public interface SprintRepository extends CrudRepository<Sprint, Long> {
    Iterable<Sprint> findByIdUser(long idUser);
}
