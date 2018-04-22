package de.web.ngthi;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.web.ngthi.item.IItemDAO;
import de.web.ngthi.item.Item;

@Configuration
@RestController
@RequestMapping("users/{userid}/items/")
public class ItemController {

	private IItemDAO itemRepository;
	
	@Autowired
	ItemController(IItemDAO itemRepository) {
		this.itemRepository = itemRepository;
	}
	

	@GetMapping
	public Iterable<Item> getItems(
			@PathVariable(value = "userid") int userID,
			@RequestParam(value = "year", required = false) Optional<Integer> year,
			@RequestParam(value = "month", required = false) Optional<Integer> month,
			@RequestParam(value = "day", required = false) Optional<Integer> day,
			@RequestParam(value = "name", required = false) Optional<String> itemname,
			@RequestParam(value = "price", required = false) Optional<Double> price,
			@RequestParam(value = "location", required = false) Optional<String> location,
			@RequestParam(value = "limit", required = false) Optional<Integer> limit){
		
		return itemRepository.query(userID, year, month, day, itemname, price, location, limit);
	}
	
	@PostMapping
	public Iterable<Item> addItems(
			@PathVariable(value = "userid") int userID,
			@RequestBody Iterable<Item> items) {
		return itemRepository.save(items);
	}
	
	@PutMapping
	public void updateItem(
			@PathVariable(value = "userid") int userID,
			@RequestParam(value = "itemID") int itemID,
			@RequestBody Item modItem) {
		itemRepository.update(itemID, modItem);
	}
	
	@DeleteMapping
	public Iterable<Item> removeItems(
			@PathVariable(value = "userid") int userID,
			@RequestBody Iterable<Integer> itemIDs) {
		return itemRepository.remove(itemIDs);
	}

}
