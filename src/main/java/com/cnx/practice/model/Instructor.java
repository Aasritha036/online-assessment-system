package com.cnx.practice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
public class Instructor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long instructorId; 
	
	@Column
	private String instructorName;
	
	@Column
	private String email;
	
	@Column
	private String password;

	public Long getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Instructor(String instructorName, String email, String password) {
		super();
		this.instructorName = instructorName;
		this.email = email;
		this.password = password;
	}

	public Instructor(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public Instructor(Long instructorId, String instructorName, String email, String password) {
		super();
		this.instructorId = instructorId;
		this.instructorName = instructorName;
		this.email = email;
		this.password = password;
	}

	public Instructor() {
		super();
	}

}