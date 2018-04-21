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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import de.web.ngthi.user.User;
import de.web.ngthi.user.UserDAO;
import de.web.ngthi.user.UserNotFoundException;
import de.web.ngthi.user.UserResource;

@Configuration
@RestController
@RequestMapping(value = "/users", produces = "application/hal+json")
public class UserController {
	
	private UserDAO userRepository;
	
	@Autowired
	UserController(UserDAO userRepository) {
		this.userRepository = userRepository;
	}
	
	@PostMapping
	public ResponseEntity<UserResource> createUser(@RequestBody final User user) {
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
	public ResponseEntity<UserResource> get(@RequestParam(value = "userid") int userID) {
		return userRepository
				.findById(userID)
				.map(u -> ResponseEntity.ok(new UserResource(u)))
				.orElseThrow(() -> new UserNotFoundException(userID));
	}
	
	@DeleteMapping("/{userid}")
	public ResponseEntity<UserResource> deleteUser(@PathVariable(value = "userid") int userid) {
		return userRepository
				.findById(userid)
				.map(u -> {
					User delU = userRepository.delete(userid);
					return ResponseEntity.ok(new UserResource(delU));
				}).orElseThrow(() -> new UserNotFoundException(userid));
	}
	
	@PutMapping("/{userid}")
	public ResponseEntity<UserResource> updateUser(
				@PathVariable(value = "userid") int userid,
				@RequestBody User userUpdated) {
		return userRepository
				.findById(userid)
				.map(u -> {
					if(userRepository.findByName(userUpdated.getUsername()).isPresent())
						throw new DuplicateUserNameException(userUpdated.getUsername());
					User updated = userRepository.update(userid, userUpdated);
					return ResponseEntity.ok(new UserResource(updated));
				}).orElseThrow(() -> new UserNotFoundException(userid));
	}

}
