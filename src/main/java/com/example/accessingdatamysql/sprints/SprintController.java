package com.example.accessingdatamysql.sprints;

import com.example.accessingdatamysql.goals.Goal;
import com.example.accessingdatamysql.goals.GoalsController;
import com.example.accessingdatamysql.users.User;
import com.example.accessingdatamysql.users.UserController;
import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
                                                        @PathVariable("idSprint") Long idSprint) {
        ResponseEntity<User> userEntity = userController.getUserByLogin(login);
        if (userEntity.getStatusCode() == HttpStatus.OK)
        {
            Optional<Sprint> sprint = sprintRepository.findById(idSprint);
            if (sprint.isPresent() && sprint.get().getIdUser() == userEntity.getBody().getId())
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
            Optional<Sprint> sprint = sprintRepository.findById(idSprint);
            if (sprint.isPresent() && sprint.get().getIdUser() == userEntity.getBody().getId())
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
            Optional<Sprint> sprint = sprintRepository.findById(idSprint);
            if (sprint.isPresent() && sprint.get().getIdUser() == user.getId()) {
                //sprint.get().setStartDate(newSprint.getStartDate().toString());
                //sprint.get().setEndDate(newSprint.getEndDate().toString());
                newSprint.setId(idSprint);
                return new ResponseEntity<>(sprintRepository.save(newSprint), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
