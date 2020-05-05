package com.example.goalssetting.controllers;

import com.example.goalssetting.entity.UserInfo;
import com.example.goalssetting.security.IAuthenticationFacade;
import com.example.goalssetting.entity.Role;
import com.example.goalssetting.entity.User;
import com.example.goalssetting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping(path="/registration")
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> registrateUser(@RequestBody User user) {
        Optional<User> userFromDB = userRepository.findByLogin(user.getLogin());

        if (userFromDB.isPresent()) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(new UserInfo(user.getLogin(), user.getPassword()), HttpStatus.CREATED);
    }

}
