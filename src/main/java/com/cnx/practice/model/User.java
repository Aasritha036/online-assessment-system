package com.cnx.practice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
public class User {
	
	private Long Id;
	
	private String userName;
	
	private String userEmail;
	
	private String password;
	
	private String role;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long long1) {
		Id = long1;
	}

	public User() {
		super();
	}

	public User(Long id, String userName, String userEmail, String password, String role) {
		super();
		Id = id;
		this.userName = userName;
		this.userEmail = userEmail;
		this.password = password;
		this.role = role;
	}

}
