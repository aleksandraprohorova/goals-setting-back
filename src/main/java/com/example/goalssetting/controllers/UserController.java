package com.example.goalssetting.controllers;

import com.example.goalssetting.entity.UserInfo;
import com.example.goalssetting.security.IAuthenticationFacade;
import com.example.goalssetting.entity.User;
import com.example.goalssetting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IAuthenticationFacade authenticationFacade;


	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public @ResponseBody
	ResponseEntity<UserInfo> authorize() {
		Authentication authentication = authenticationFacade.getAuthentication();
		Optional<User> user = userRepository.findByLogin(authentication.getName());

		if (authentication.getName().equals("anonymousUser") || !user.isPresent()) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(new UserInfo(authentication.getName(), user.get().getPassword()), HttpStatus.OK);
	}

	@RequestMapping(value = "/{login}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<User>
	getUserByLogin(@PathVariable("login") String login) {
		Authentication authentication = authenticationFacade.getAuthentication();
		if (!authentication.getName().equals(login)) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Optional<User> user = userRepository.findByLogin(login);
		return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@RequestMapping(value = "/username", method = RequestMethod.GET)
	@ResponseBody
	public String currentUserName() {
		Authentication authentication = authenticationFacade.getAuthentication();
		return authentication.getName();
	}


	@RequestMapping(value = "/{login}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteByLogin(@PathVariable String login) {
		Authentication authentication = authenticationFacade.getAuthentication();
		if (!authentication.getName().equals(login)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		Optional<User> tmp = userRepository.findByLogin(login);
		if (tmp.isPresent())
		{
			userRepository.delete(tmp.get());
			return new ResponseEntity<>("User is deleted successsfully", HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<>("User is not found", HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Iterable<User>> getAllUsers() {
		return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
	}


}
