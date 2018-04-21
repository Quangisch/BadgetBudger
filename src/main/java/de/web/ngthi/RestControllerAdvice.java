package de.web.ngthi;

import java.util.Optional;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import de.web.ngthi.user.DuplicateUserException;
import de.web.ngthi.user.UserNotFoundException;

@ControllerAdvice
public class RestControllerAdvice {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<VndErrors> notFoundException(final UserNotFoundException e) {
		return error(e, HttpStatus.NOT_FOUND);
	}

	private ResponseEntity<VndErrors> error(final Exception exception, final HttpStatus httpStatus) {
		final String message = Optional.of(exception.getMessage()).orElse(exception.getClass().getSimpleName());
		return new ResponseEntity<>(new VndErrors(exception.getClass().getSimpleName(), message), httpStatus);
	}
	
	 @ExceptionHandler(DuplicateUserException.class)
	  public ResponseEntity<VndErrors> duplicateException(final DuplicateUserException e) {
	    return error(e, HttpStatus.BAD_REQUEST);
	  }
}
