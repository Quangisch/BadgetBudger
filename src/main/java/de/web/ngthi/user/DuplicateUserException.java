package de.web.ngthi.user;

@SuppressWarnings("serial")
public class DuplicateUserException extends RuntimeException {

	public DuplicateUserException(String username) {
		super("User with username '" + username + "' already exists.");
	}
}