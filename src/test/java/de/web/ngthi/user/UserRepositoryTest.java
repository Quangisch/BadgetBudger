package de.web.ngthi.user;


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

@SpringBootTest
@Transactional
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	final Logger logger = LoggerFactory.getLogger(UserRepository.class);
	
	@Test
	public void testCreateUser() {
		int oldSize = userRepository.getUserList().size();
		String username = "Erwin";
		
		userRepository.create(username);
		assertThat(userRepository.getUserList().size(), is(oldSize + 1));
	}
	
	@Test(expected = DuplicateKeyException.class)
	public void testcreateDuplicateUser() {
		testCreateUser();
		testCreateUser();
	}
	
	@Test
	public void testGetUser() {
		String username = "Aladin";
		
		User u = userRepository.getUser(username);
		assertThat(u.getUsername(), is(username));
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testGetNonexistantUser() {
		String username = "Aladin3";
		
		userRepository.getUser(username);
	}
	
	@Test
	public void testUpdateUser() {
		User u = userRepository.getUserList().get(0);
		String oldname = u.getUsername();
		String newname = "Raichu";
		logger.info(oldname + "->" + newname);
		
		userRepository.update(oldname, newname);
		
	}

	@Test
	public void testDeleteUser() {
		String username = "Aladin";
		int sizeB = userRepository.getUserList().size();
		userRepository.delete(username);
		int sizeNow = userRepository.getUserList().size();
		assertThat(sizeNow, is(sizeB - 1));
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testDeleteNonExistantUser() {
		String username = "Aladin3";
		userRepository.delete(username);
	}

	@Test
	public void testGetAllUsers() {
		List<User> users = userRepository.getUserList();
		
		assertThat(users, allOf(notNullValue(), IsCollectionWithSize.hasSize(6)));
	}

}
