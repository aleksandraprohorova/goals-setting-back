package com.example.accessingdatamysql.sprints;

import com.example.accessingdatamysql.sprints.Sprint;
import org.springframework.data.repository.CrudRepository;

public interface SprintRepository extends CrudRepository<Sprint, Long> {
    Iterable<Sprint> findByIdUser(long idUser);
}
