package de.web.ngthi.item;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Optional;

import org.springframework.hateoas.ResourceSupport;

import de.web.ngthi.ItemController;

public class ItemResource extends ResourceSupport {

	private final Item item;
	
	public ItemResource(Item item) {
		this.item = item;
		int userID = item.getUserID();
		this.add(linkTo(ItemController.class, userID).withRel("items"));
		this.add(linkTo(methodOn(ItemController.class, userID).getItems(userID, 
				Optional.of(item.getYear()), Optional.of(item.getMonth()), Optional.of(item.getDay()), 
				Optional.of(item.getName()), Optional.of(item.getPrice()), Optional.of(item.getLocation()), Optional.of(1)))
				.withSelfRel());
	}
	
	public Item getItem() {
		return item;
	}
	
}
