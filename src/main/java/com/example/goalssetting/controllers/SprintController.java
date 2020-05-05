package com.example.goalssetting.controllers;

import com.example.goalssetting.entity.Sprint;
import com.example.goalssetting.repositories.SprintRepository;
import com.example.goalssetting.entity.User;
import com.example.goalssetting.repositories.UserRepository;
import com.example.goalssetting.security.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="users/sprints")
public class SprintController {
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    GoalsController goalsController;

    @GetMapping(value="/{idSprint}")
    public @ResponseBody ResponseEntity<Sprint>
    getSprintById(@PathVariable("idSprint") Long idSprint) {

        Authentication authentication = authenticationFacade.getAuthentication();
        String login = authentication.getName();
        Optional<User> user = userRepository.findByLogin(login);

        if (user.isPresent())
        {
            List<Sprint> sprints = (List<Sprint>) sprintRepository.findByIdUser(user.get().getId());
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
    public @ResponseBody ResponseEntity<Iterable<Sprint>> getAllSprints() {

        Authentication authentication = authenticationFacade.getAuthentication();
        String login = authentication.getName();
        Optional<User> user = userRepository.findByLogin(login);

        return user.map(value -> new ResponseEntity<>(sprintRepository.findByIdUser(value.getId()), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object>
    add(@RequestBody Sprint sprint) {

        Authentication authentication = authenticationFacade.getAuthentication();
        String login = authentication.getName();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            login = ((UserDetails)principal).getUsername();
        } else {
            login = principal.toString();
        }
        Optional<User> user = userRepository.findByLogin(login);

        if (user.isPresent()) {
            sprint.setIdUser(user.get().getId());
            sprintRepository.save(sprint);
            return new ResponseEntity<>(sprint.getId(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value="/{idSprint}", method = RequestMethod.DELETE)
    public ResponseEntity<Object>
    delete(@PathVariable("idSprint") Long idSprint) {

        Authentication authentication = authenticationFacade.getAuthentication();
        String login = authentication.getName();
        Optional<User> user = userRepository.findByLogin(login);

        if (user.isPresent()) {
            List<Sprint> sprints = (List<Sprint>) sprintRepository.findByIdUser(user.get().getId());
            final Optional<Sprint> sprint = sprints.stream()
                    .filter(s -> s.getId() == idSprint)
                    .findAny();
            if (sprint.isPresent())
            {
                goalsController.deleteBySprintId(idSprint);
                sprintRepository.delete(sprint.get());
                return new ResponseEntity<>(sprint, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/{idSprint}")
    public @ResponseBody ResponseEntity<Sprint>
    replaceSprint(@RequestBody Sprint newSprint,
                  @PathVariable("idSprint") Long idSprint) {

        Authentication authentication = authenticationFacade.getAuthentication();
        String login = authentication.getName();
        Optional<User> user = userRepository.findByLogin(login);

        if (user.isPresent()) {
            List<Sprint> sprints = (List<Sprint>) sprintRepository.findByIdUser(user.get().getId());
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
