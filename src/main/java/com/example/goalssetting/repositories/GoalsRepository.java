package com.example.goalssetting.repositories;

import com.example.goalssetting.entity.Goal;
import org.springframework.data.repository.CrudRepository;

public interface GoalsRepository extends CrudRepository<Goal, Long> {
    Iterable<Goal> findByIdSprint(long idSprint);
}
