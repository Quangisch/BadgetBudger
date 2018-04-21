package de.web.ngthi.dbms;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import de.web.ngthi.item.Item;
import de.web.ngthi.item.ItemRepository;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ItemRepositoryTest {

	@Autowired
	private ItemRepository itemRepository;
	final Logger logger = LoggerFactory.getLogger(ItemRepositoryTest.class);
	
	private Optional<Integer> optInteger = Optional.empty();
	private Optional<Double> optDouble = Optional.empty();
	private Optional<String> optString = Optional.empty();
	
	@Test
	public void testGetAllItems() {
		List<Item> users = itemRepository.getAllItems(1);
		assertThat(users, allOf(notNullValue(), IsCollectionWithSize.hasSize(4)));
	}
	
	@Test
	public void testGetItems1() {
		int userID = 1;
		List<Item> uList1 = itemRepository.getItems(userID, optInteger, optInteger, optInteger, optString, optDouble, optString, optInteger);
		List<Item> uList2 = itemRepository.getAllItems(userID);
		assertThat(uList1.size(), is(equalTo(uList2.size())));
	}

	@Test
	public void testGetItems2() {
		int userID = 1;
		Optional<Integer> year = Optional.of(2007);
		List<Item> uList1 = itemRepository.getItems(userID, year, optInteger, optInteger, optString, optDouble, optString, optInteger);
		assertThat(uList1.size(), is(equalTo(1)));
		assertThat(uList1.get(0).getUserID(), is(equalTo(userID)));
		assertThat(uList1.get(0).getYear(), is(equalTo(year.get())));
	}
	
	@Test
	public void testGetItems3() {
		int userID = 1;
		Optional<Integer> year = Optional.of(2005);
		Optional<Integer> day = Optional.of(1);
		List<Item> uList1 = itemRepository.getItems(userID, year, optInteger, day, optString,  optDouble, optString, optInteger);
		assertThat(uList1.size(), is(equalTo(2)));
	}
	
	@Test
	public void testGetItems4() {
		int userID = 5;
		Optional<Integer> limit = Optional.of(3);
		List<Item> uList1 = itemRepository.getItems(userID, optInteger, optInteger, optInteger, optString, optDouble, optString, limit);
		assertThat(uList1.size(), is(equalTo(3)));
	}
	
	@Test
	public void testAddItems() {
		int userID = 11;
		List<Item> items = new LinkedList<>();
		int itemsCount = itemRepository.getAllItems(userID).size();
		
		items.add(new Item(userID, 2009, 10, 3, "Bluse1", 29.95, "Dortmund", 1));
		items.add(new Item(userID, 2009, 10, 3, "Bluse2", 29.95, "Dortmund", 2));
		items.add(new Item(userID, 2009, 10, 4, "Bluse3", 29.95, "Dortmund", 1));
		items.add(new Item(userID, 2009, 11, 3, "Bluse4", 29.95, "Bonn", 1));
		itemRepository.addItems(items.toArray(new Item[items.size()]));
		
		int itemsCountNew = itemRepository.getAllItems(userID).size();
		assertThat(itemsCountNew, is(equalTo(itemsCount + items.size())));
	}
	
	@Test
	public void testModifyItem() {
		Item modItem = new Item(1, 2009, 10, 3, "Bluse1", 29.95, "Dortmund", 1);
		int itemsCount = itemRepository.getAllItems(1).size();
		itemRepository.modifyItem(1, modItem);
		int itemsCountNew = itemRepository.getAllItems(1).size();
		assertThat(itemsCountNew, is(equalTo(itemsCount)));
	}
	
	@Test
	public void testRemoveItems() {
		int[] itemIDS = new int[] {1, 2, 3, 6, 8, 9};
		Item[] removed = itemRepository.removeItems(itemIDS);
		assertThat(removed.length, is(equalTo(itemIDS.length)));
		assertThat(itemRepository.getAllItems(1).size(), is(equalTo(1)));
	}
	
	@Test
	public void testRemoveItems2() {
		int[] itemIDS = new int[] {99};
		Item[] removed = itemRepository.removeItems(itemIDS);
		assertThat(removed.length, is(equalTo(0)));
	}
}
