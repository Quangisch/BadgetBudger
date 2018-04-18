package de.web.ngthi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@SuppressWarnings("serial")
class DuplicateUserException extends RuntimeException {

	public DuplicateUserException(String username) {
		super("User '" + username + "' already exists.");
	}
}