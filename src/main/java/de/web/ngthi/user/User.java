package de.web.ngthi.user;

public class User {

	private int id;
	private String name;

	private User() {
		
	} //for JPA
	
	public User(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String toString() {
		return String.format("User: %s", name);
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof User))
			return false;
		User u = (User) o;
		return id == u.id && name.equals(u.name);
	}

}
