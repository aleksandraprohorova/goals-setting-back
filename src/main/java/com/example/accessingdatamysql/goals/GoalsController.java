package com.example.accessingdatamysql.goals;

import com.example.accessingdatamysql.sprints.Sprint;
import com.example.accessingdatamysql.sprints.SprintController;
import com.example.accessingdatamysql.users.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import java.util.Optional;

@Controller
@RequestMapping(path="users/{login}/sprints/{idSprint}/goals")
public class GoalsController {
    @Autowired
    private GoalsRepository goalsRepository;

    @Autowired
    private SprintController sprintController;

    @GetMapping(path="/{idGoal}")
    public @ResponseBody ResponseEntity<Goal> getGoalById(@PathVariable("login") String login,
                                                            @PathVariable("idSprint") Long idSprint,
                                                            @PathVariable("idGoal") Long idGoal) {
        ResponseEntity<Sprint> sprintEntity = sprintController.getSprintById(login, idSprint);
        if (sprintEntity.getStatusCode() == HttpStatus.OK)
        {
            Sprint sprint = sprintEntity.getBody();
            Optional<Goal> goal = goalsRepository.findById(idGoal);
            if (sprint.getId() == goal.get().getIdSprint()) {
                return new ResponseEntity<>(goal.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public @ResponseBody ResponseEntity<Iterable<Goal>> getAllGoals(@PathVariable("login") String login,
                                                    @PathVariable("idSprint") Long idSprint) {
        ResponseEntity<Sprint> sprintEntity = sprintController.getSprintById(login, idSprint);
        if (sprintEntity.getStatusCode() == HttpStatus.OK) {
            return new ResponseEntity<>(goalsRepository.findByIdSprint(idSprint), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> add(@RequestBody Goal goal,
                                      @PathVariable("login") String login,
                                      @PathVariable("idSprint") Long idSprint) {
        ResponseEntity<Sprint> sprintEntity = sprintController.getSprintById(login, idSprint);
        if (sprintEntity.getStatusCode() == HttpStatus.OK) {
            Sprint sprint = sprintEntity.getBody();
            goal.setIdSprint(sprint.getId());
            goalsRepository.save(goal);
            return new ResponseEntity<>(goal, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable("login") String login,
                                         @PathVariable("idSprint") Long idSprint,
                                         @PathVariable("idGoal") Long idGoal) {

        ResponseEntity<Sprint> sprintEntity = sprintController.getSprintById(login, idSprint);
        if (sprintEntity.getStatusCode() == HttpStatus.OK) {
            Sprint sprint = sprintEntity.getBody();
            Optional<Goal> goal = goalsRepository.findById(idGoal);
            if (goal.isPresent() && goal.get().getIdSprint() == sprint.getId()) {
                goalsRepository.delete(goal.get());
                return new ResponseEntity<>("Goal is deleted successsfully", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Goal is not found", HttpStatus.NOT_FOUND);
    }

    @PutMapping(path = "/{idGoal}")
    public @ResponseBody
    ResponseEntity<Goal> replaceGoal(@RequestBody Goal newGoal,
                                     @PathVariable("login") String login,
                                     @PathVariable("idSprint") Long idSprint,
                                     @PathVariable("idGoal") Long idGoal) {

        ResponseEntity<Sprint> sprintEntity = sprintController.getSprintById(login, idSprint);
        if (sprintEntity.getStatusCode() == HttpStatus.OK) {
            Sprint sprint = sprintEntity.getBody();
            Optional<Goal> goal = goalsRepository.findById(idGoal);
            if (goal.isPresent() && goal.get().getIdSprint() == sprint.getId()) {
                goal.get().setDescription(newGoal.getDescription());
                goal.get().setIsDone(newGoal.getIsDone());
                return new ResponseEntity<>(goalsRepository.save(goal.get()), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
