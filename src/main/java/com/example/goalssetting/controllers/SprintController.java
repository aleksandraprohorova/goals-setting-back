package com.example.goalssetting.controllers;

import com.example.goalssetting.entity.Sprint;
import com.example.goalssetting.repositories.SprintRepository;
import com.example.goalssetting.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="users/{login}/sprints")
public class SprintController {
    @Autowired
    private SprintRepository sprintRepository;
    @Autowired
    UserController userController;

    @Autowired
    GoalsController goalsController;


    @GetMapping(value="/{idSprint}")
    public @ResponseBody ResponseEntity<Sprint> getSprintById(@PathVariable("login") String login,
                                                              @PathVariable("idSprint") Long idSprint
                                                              ) {
        ResponseEntity<User> userEntity = userController.getUserByLogin(login);

        if (userEntity.getStatusCode() == HttpStatus.OK)
        {
            User user = userEntity.getBody();
            List<Sprint> sprints = (List<Sprint>) sprintRepository.findByIdUser(user.getId());
            final Optional<Sprint> sprint = sprints.stream()
                    .filter(s -> s.getId() == idSprint)
                    .findAny();
            if (sprint.isPresent())
            {
                return new ResponseEntity<>(sprint.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public @ResponseBody ResponseEntity<Iterable<Sprint>> getAllSprints(@PathVariable("login") String login) {
        ResponseEntity<User> userEntity = userController.getUserByLogin(login);
        if (userEntity.getStatusCode() == HttpStatus.OK)
        {
            return new ResponseEntity<>(sprintRepository.findByIdUser(userEntity.getBody().getId()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> add(@RequestBody Sprint sprint, @PathVariable("login") String login) {
        ResponseEntity<User> userEntity = userController.getUserByLogin(login);
        if (userEntity.getStatusCode() == HttpStatus.OK) {
            sprint.setIdUser(userEntity.getBody().getId());
            sprintRepository.save(sprint);
            return new ResponseEntity<>(sprint.getId(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/{idSprint}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("idSprint") Long idSprint,
                                         @PathVariable("login") String login) {
        ResponseEntity<User> userEntity = userController.getUserByLogin(login);
        if (userEntity.getStatusCode() == HttpStatus.OK) {
            User user = userEntity.getBody();
            List<Sprint> sprints = (List<Sprint>) sprintRepository.findByIdUser(user.getId());
            final Optional<Sprint> sprint = sprints.stream()
                    .filter(s -> s.getId() == idSprint)
                    .findAny();
            if (sprint.isPresent())
            {
                goalsController.deleteBySprintId(login, idSprint);
                sprintRepository.delete(sprint.get());
                return new ResponseEntity<>(sprint, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{idSprint}")
    public @ResponseBody
    ResponseEntity<Sprint> replaceSprint(@RequestBody Sprint newSprint,
                                     @PathVariable("login") String login,
                                     @PathVariable("idSprint") Long idSprint) {

        ResponseEntity<User> userEntity = userController.getUserByLogin(login);
        if (userEntity.getStatusCode() == HttpStatus.OK) {
            User user = userEntity.getBody();
            List<Sprint> sprints = (List<Sprint>) sprintRepository.findByIdUser(user.getId());
            final Optional<Sprint> sprint = sprints.stream()
                    .filter(s -> s.getId() == idSprint)
                    .findAny();
            if (sprint.isPresent()) {
                newSprint.setId(idSprint);
                return new ResponseEntity<>(sprintRepository.save(newSprint), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
