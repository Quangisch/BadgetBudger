package de.web.ngthi.dbms;


import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import de.web.ngthi.user.User;
import de.web.ngthi.user.UserNotFoundException;
import de.web.ngthi.user.UserRepository;

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	final Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);
	
	@Test
	public void testCreateUser() {
		int oldSize = userRepository.findAll().size();
		User u = new User(99, "Erwin");
		userRepository.save(u);
		assertThat(userRepository.findAll().size(), is(oldSize + 1));
	}
	
	@Test(expected = DuplicateKeyException.class)
	public void testcreateDuplicateUser() {
		testCreateUser();
		testCreateUser();
	}
	
	@Test
	public void testGetUser() {
		int userID = 1;
		
		User u = userRepository.findById(userID).get();
		assertThat(u.getUserID(), is(userID));
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testGetNonexistantUser() {
		int userid = 123;
		
		userRepository.findById(userid);
	}
	
	@Test
	public void testUpdateUser() {
		User u = userRepository.findAll().get(0);
		int userid = u.getUserID();
		User updatedUser = new User(u.getUserID(), "Raichu");
		logger.info(userid + ":  " + u.getUsername() + "->" + updatedUser.getUsername());
		
		userRepository.update(userid, updatedUser);
		
	}

	@Test
	public void testDeleteUser() {
		int userid = 1;
		int sizeB = userRepository.findAll().size();
		userRepository.delete(userid);
		int sizeNow = userRepository.findAll().size();
		assertThat(sizeNow, is(sizeB - 1));
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testDeleteNonExistantUser() {
		int userid = 123;
		userRepository.delete(userid);
	}

	@Test
	public void testGetAllUsers() {
		List<User> users = userRepository.findAll();
		
		assertThat(users, allOf(notNullValue(), IsCollectionWithSize.hasSize(6)));
	}

}
