package de.web.ngthi;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.web.ngthi.user.User;
import de.web.ngthi.user.UserDAO;

@Configuration
@RestController
@RequestMapping("users")
public class UserRestController {
	
	private UserDAO userRepository;
	
	@Autowired
	UserRestController(UserDAO userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<User> createUser(@RequestParam(value = "username") String username) {
		User u = userRepository.create(username);
		return new ResponseEntity<>(u, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public List<User> getAllUsers() {
		List<User> models = userRepository.getUserList();
	    for (User u : models) {
	        Link selfLink = linkTo(UserRestController.class).slash(u.getUsername()).withSelfRel();
	        u.add(selfLink);
	    }
	    
	    return models;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{userid}")
	public HttpEntity<User> deleteUser(@PathVariable(value = "userid") int userid) {
		return new ResponseEntity<>(userRepository.delete(userid), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{userid}")
	public HttpEntity<User> updateUser(
				@PathVariable(value = "userid") int userid,
				@RequestParam(value = "newname") String newname) {
		return new ResponseEntity<>(userRepository.update(userid, newname), HttpStatus.OK);
	}

}
