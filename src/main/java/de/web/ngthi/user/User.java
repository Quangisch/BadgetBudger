package de.web.ngthi.user;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.hateoas.ResourceSupport;

@Entity
public class User extends ResourceSupport {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userID;
	private String username;

	@SuppressWarnings("unused")
	private User() {
		
	} //for JPA
	
	public User(User user) {
		this.username = user.username;
	}
	
	public User(int id, String name) {
		this.userID = id;
		this.username = name;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}
	
	public int getUserID() {
		return userID;
	}

	public void setUserId(int id) {
		this.userID = id;
	}
	
	public String toString() {
		return String.format("User: %s", username);
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof User))
			return false;
		User u = (User) o;
		return userID == u.userID && username.equals(u.username);
	}

}
