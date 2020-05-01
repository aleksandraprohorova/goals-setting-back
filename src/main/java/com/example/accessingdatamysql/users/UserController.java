package com.example.accessingdatamysql.users;

import com.example.accessingdatamysql.security.IAuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping(path="/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IAuthenticationFacade authenticationFacade;


	@RequestMapping(value = "/{login}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<User> getUserByLogin(@PathVariable("login") String login){
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


}
