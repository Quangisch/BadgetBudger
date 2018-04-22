package de.web.ngthi;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.web.ngthi.user.User;
import de.web.ngthi.user.IUserDAO;
import de.web.ngthi.user.UserResource;

@Configuration
@RestController
@RequestMapping(value = "/users", produces = "application/hal+json")
public class UserController {
	
	private IUserDAO userRepository;
	
	@Autowired
	UserController(IUserDAO userRepository) {
		this.userRepository = userRepository;
	}
	
	@PostMapping
	public ResponseEntity<UserResource> addUser(@RequestBody final User user) {
		User newUser = new User(user);
		newUser = userRepository.save(newUser);
		final URI uri = MvcUriComponentsBuilder.fromController(getClass())
				.path("/{userid}").buildAndExpand(newUser.getUserID()).toUri();
		return ResponseEntity.created(uri).body(new UserResource(newUser));
	}
	
	@GetMapping
	public ResponseEntity<Resources<UserResource>> getAllUsers() {
		final List<UserResource> collection = userRepository.findAll().stream().map(UserResource::new).collect(Collectors.toList());
		final Resources<UserResource> resources = new Resources<>(collection);
		final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().build().toUriString();
		resources.add(new Link(uriString, "self"));
		return ResponseEntity.ok(resources);
	}
	
	@GetMapping("/{userid}")
	public ResponseEntity<UserResource> get(@PathVariable(value = "userid") int userID) {
		User u = userRepository.findById(userID);
		return ResponseEntity.ok(new UserResource(u));
	}
	
	@DeleteMapping("/{userid}")
	public ResponseEntity<UserResource> removeUser(@PathVariable(value = "userid") int userid) {
		User delU = userRepository.delete(userid);
		return ResponseEntity.ok(new UserResource(delU));
	}
	
	@PutMapping("/{userid}")
	public ResponseEntity<UserResource> updateUser(
				@PathVariable(value = "userid") int userid,
				@RequestBody User userUpdated) {
		User updated = userRepository.update(userid, userUpdated);
		return ResponseEntity.ok(new UserResource(updated));
	}

}
