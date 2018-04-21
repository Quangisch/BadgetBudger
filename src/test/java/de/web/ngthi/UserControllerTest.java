package de.web.ngthi;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import de.web.ngthi.user.User;
import de.web.ngthi.user.UserResource;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	final String PATH = "/users";
	
	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserController userController;
	
	
	@Test
	public void testCreateUser() throws Exception {
		User u = new User(2, "Tom");
		given(userController.createUser(u)).willReturn(ResponseEntity.ok(new UserResource(u)));
		mvc.perform(post(PATH).param("username", u.getUsername())
			.contentType(APPLICATION_JSON))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.username", is(u.getUsername())));
	}
	
	public void testCreateExistingUser() throws Exception {
		//Todo
	}
	
	@Test
	public void testGetAllUsers() throws Exception {
		List<User> l = Arrays.asList(new User(2, "Tom"), new User(3, "Phil"));
		Resources<UserResource> resources = new Resources<>(l.stream().map(UserResource::new).collect(Collectors.toList()));
		given(userController.getAllUsers()).willReturn(ResponseEntity.ok(resources));
		
		mvc.perform(get(PATH)
			.contentType(APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(l.size())))
			.andExpect(jsonPath("$[0].username", is(l.get(0).getUsername())))
			.andExpect(jsonPath("$[1].username", is(l.get(1).getUsername())));
	}
	
	
	public void deleteUser() throws Exception{
		//TODO
	}
	
	public void deletNonExistantUser() throws Exception {
		//TODO
	}
	
	@Test
	public void testUpdateUser() throws Exception {
		User oldUser = new User(2, "Tom");
		User newUser = new User(2, "Tommy");
		
		given(userController.updateUser(oldUser.getUserID(), newUser))
				.willReturn(ResponseEntity.ok(new UserResource(newUser)));
		
		mvc.perform(put(PATH + "/" + oldUser.getUserID()).param("newname", newUser.getUsername())
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.username", is(newUser.getUsername())))
				.andExpect(jsonPath("$.userID", is(newUser.getUserID())));
	}
	
	
	public void testUpdateNonexistingUser() throws Exception {
		//TODO
	}
	
	
	public void testUpdateUserDuplicate() throws Exception {
		//TODO
	}
}