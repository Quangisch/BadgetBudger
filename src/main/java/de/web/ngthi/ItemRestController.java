package de.web.ngthi;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.web.ngthi.item.Item;
import de.web.ngthi.item.ItemDAO;

@Configuration
@RestController
@RequestMapping("users/{userid}/items/")
public class ItemRestController {

	private ItemDAO itemRepository;
	
	@Autowired
	ItemRestController(ItemDAO itemRepository) {
		this.itemRepository = itemRepository;
	}
	

	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/all")
	@ResponseStatus(HttpStatus.OK)
	public List<Item> getAllItems(
			@PathVariable(value = "userid") int userID) {
		return itemRepository.getAllItems(userID);
	}

}
