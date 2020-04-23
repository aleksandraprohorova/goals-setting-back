package com.example.accessingdatamysql.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody User user) {
		userRepository.save(user);
		return new ResponseEntity<>("User is created successfully", HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@RequestBody Long id) {

		Optional<User> tmp = userRepository.findById(id);
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

	@RequestMapping(value = "/{login}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<User> getUserByLogin(@PathVariable("login") String login){
		Optional<User> user = userRepository.findByLogin(login);
		if (user.isPresent())
		{
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
