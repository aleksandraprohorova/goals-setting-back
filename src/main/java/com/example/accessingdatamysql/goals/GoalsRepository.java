package com.example.accessingdatamysql.goals;

import org.springframework.data.repository.CrudRepository;

public interface GoalsRepository extends CrudRepository<Goal, Long> {
    Iterable<Goal> findByIdSprint(long idSprint);
}
