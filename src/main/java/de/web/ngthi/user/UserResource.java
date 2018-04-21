package de.web.ngthi.user;

import org.springframework.hateoas.ResourceSupport;

import de.web.ngthi.ItemController;
import de.web.ngthi.UserController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Optional;

public class UserResource extends ResourceSupport {

	private final User user;
	
	public UserResource(User user) {
		this.user = user;
		int userID = user.getUserID();
		
		add(linkTo(UserController.class, userID).withRel("users"));
		add(linkTo(methodOn(ItemController.class, userID)
					.getItems(userID, Optional.empty(), Optional.empty(), Optional.empty(), 
							Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()))
					.withRel("items"));
		add(linkTo(methodOn(UserController.class, userID).get(userID)).withSelfRel());
	}
	
	public User getUser() {
		return user;
	}
}
