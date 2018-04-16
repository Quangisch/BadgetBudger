package de.web.ngthi;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.web.ngthi.item.ItemDAO;
import de.web.ngthi.user.User;
import de.web.ngthi.user.UserDAO;

@Configuration
@RestController
@RequestMapping("users")
public class UserRestController {
	
	private ItemDAO itemRepository;
	private UserDAO userRepository;
	
	@Autowired
	UserRestController(ItemDAO itemRepository, UserDAO userRepository) {
		this.itemRepository = itemRepository;
		this.userRepository = userRepository;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void createUser(@RequestParam(value = "username") String username) {
		userRepository.create(username);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public Collection<User> getAlleUsers() {
		return userRepository.getUserList();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{username}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable(value = "username") String username) {
		userRepository.delete(username);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{username}")
	@ResponseStatus(HttpStatus.OK)
	public void updateUser(
				@RequestParam(value = "newname") String newName,
				@PathVariable(value = "username") String username) {
		userRepository.update(username, newName);
	}

}
