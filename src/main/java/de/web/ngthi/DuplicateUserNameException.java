package de.web.ngthi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@SuppressWarnings("serial")
class DuplicateUserNameException extends RuntimeException {

	public DuplicateUserNameException(String username) {
		super("User '" + username + "' already exists.");
	}
}