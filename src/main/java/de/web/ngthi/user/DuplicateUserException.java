package de.web.ngthi.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@SuppressWarnings("serial")
public class DuplicateUserException extends RuntimeException {

	public DuplicateUserException(String username) {
		super("User '" + username + "' already exists.");
	}
}