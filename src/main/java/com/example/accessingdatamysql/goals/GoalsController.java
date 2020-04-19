package com.example.accessingdatamysql.goals;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/goals")
public class GoalsController {
    @Autowired
    private GoalsRepository goalsRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Object> add(@RequestBody Goal goal) {
        goalsRepository.save(goal);
        return new ResponseEntity<>(goal, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@RequestBody Long id) {

        Optional<Goal> tmp = goalsRepository.findById(id);
        if (tmp.isPresent())
        {
            goalsRepository.delete(tmp.get());
            return new ResponseEntity<>("Goal is deleted successsfully", HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Goal is not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path="/")
    public @ResponseBody
    Iterable<Goal> getAllGoals() {
        return goalsRepository.findAll();
    }

    @GetMapping(path = "/{idSprint}")
    public @ResponseBody Iterable<Goal> getAllGoals(@PathVariable("idSprint") long idSprint) {
        return goalsRepository.findByIdSprint(idSprint);
    }

    @PutMapping(path = "/{id}")
    public @ResponseBody
    Goal replaceGoal(@RequestBody Goal newGoal, @PathVariable("id") long id) {

        return goalsRepository.findById(id)
                .map(goal -> {
                    goal.setDescription(newGoal.getDescription());
                    goal.setIsDone(newGoal.getIsDone());
                    return goalsRepository.save(goal);
                })
                .orElseGet(() -> {
                    newGoal.setId(id);
                    return goalsRepository.save(newGoal);
                });
    }
}
