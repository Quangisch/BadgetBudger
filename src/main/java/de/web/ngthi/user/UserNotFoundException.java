package de.web.ngthi.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@SuppressWarnings("serial")
public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException(int id) {
		super("could not find user '" + id + "'.");
	}
}