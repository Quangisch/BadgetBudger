package de.web.ngthi.item;

@SuppressWarnings("serial")
public class ItemNotFoundException extends RuntimeException {

	public ItemNotFoundException(int itemID) {
		super("No Item with id:" + itemID);
	}
	
}
