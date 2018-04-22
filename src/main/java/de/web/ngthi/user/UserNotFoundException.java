package de.web.ngthi.user;

@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(int id) {
		super("No User with id: " + id);
	}
}