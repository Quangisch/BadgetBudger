package de.web.ngthi;

import java.util.List;
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

import de.web.ngthi.item.Item;
import de.web.ngthi.item.ItemDAO;

@Configuration
@RestController
@RequestMapping("users/{userid}/items/")
public class ItemController {

	private ItemDAO itemRepository;
	
	@Autowired
	ItemController(ItemDAO itemRepository) {
		this.itemRepository = itemRepository;
	}
	

	@GetMapping
	public List<Item> getItems(
			@PathVariable(value = "userid") int userID,
			@RequestParam(value = "year", required = false) Optional<Integer> year,
			@RequestParam(value = "month", required = false) Optional<Integer> month,
			@RequestParam(value = "day", required = false) Optional<Integer> day,
			@RequestParam(value = "name", required = false) Optional<String> itemname,
			@RequestParam(value = "price", required = false) Optional<Double> price,
			@RequestParam(value = "location", required = false) Optional<String> location,
			@RequestParam(value = "limit", required = false) Optional<Integer> limit){
		
		return itemRepository.getItems(userID, year, month, day, itemname, price, location, limit);
	}
	
	@PostMapping
	public Item[] addItems(
			@PathVariable(value = "userid") int userID,
			@RequestBody Item[] items) {
		return itemRepository.addItems(items);
	}
	
	@PutMapping
	public void modifyItem(
			@PathVariable(value = "userid") int userID,
			@RequestParam(value = "itemID") int itemID,
			@RequestBody Item modItem) {
		itemRepository.modifyItem(itemID, modItem);
		
	}
	
	@DeleteMapping
	public Item[] removeItems(
			@PathVariable(value = "userid") int userID,
			@RequestBody int[] itemIDs) {
		return itemRepository.removeItems(itemIDs);
	}

}
