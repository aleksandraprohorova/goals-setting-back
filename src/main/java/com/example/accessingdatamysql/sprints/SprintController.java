package com.example.accessingdatamysql.sprints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/sprints")
public class SprintController {
    @Autowired
    private SprintRepository sprintRepository;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Object> add(@RequestBody Sprint sprint) {
        sprintRepository.save(sprint);
        return new ResponseEntity<>(sprint.getId(), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@RequestBody Long id) {

        Optional<Sprint> tmp = sprintRepository.findById(id);
        if (tmp.isPresent())
        {
            sprintRepository.delete(tmp.get());
            return new ResponseEntity<>("Sprint is deleted successsfully", HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>("Sprint is not found", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(path="/{idUser}")
    public @ResponseBody Iterable<Sprint> getAllSprints(@PathVariable("idUser") long idUser) {
        return sprintRepository.findByIdUser(idUser);
    }

    @GetMapping(path="/")
    public @ResponseBody Iterable<Sprint> getAllSprints() {
        return sprintRepository.findAll();
    }
}
