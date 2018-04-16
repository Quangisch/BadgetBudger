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

@Configuration
@RestController
public class BudgetRestController {
	
	private ItemDAO itemRepository;
	private UserDAO userRepository;
	
	@Autowired
	BudgetRestController(ItemDAO itemRepository, UserDAO userRepository) {
		this.itemRepository = itemRepository;
		this.userRepository = userRepository;
	}
	
	public void setItemRepository(ItemDAO_JDBC itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	public void setUserRepository(UserDAO_JDBC userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/users")
	@ResponseStatus(HttpStatus.OK)
	public void createUser(@RequestParam(value = "username") String username) {
		userRepository.create(username);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/users")
	@ResponseStatus(HttpStatus.OK)
	public Collection<User> getAlleUsers() {
		return userRepository.getUserList();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/users/{username}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteUser(@PathVariable(value = "username") String username) {
		userRepository.delete(username);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/users/{username}")
	@ResponseStatus(HttpStatus.OK)
	public void updateUser(
				@RequestParam(value = "newname") String newName,
				@PathVariable(value = "username") String username) {
		userRepository.update(username, newName);
	}
	
	@RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
