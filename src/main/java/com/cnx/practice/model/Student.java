package com.cnx.practice.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentId; 
	
	@Column
	private String studentName;
	
	@Column
	private String email;
	
	@Column
	private String password;
	
	@ManyToMany(mappedBy = "students", cascade = { CascadeType.ALL })
    private List<Quiz> quizes;

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
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

	public List<Quiz> getQuizes() {
		return quizes;
	}

	public List<Quiz> getQuizes(Long instructorId) {
		List<Quiz> quizList = new ArrayList<>();
		for(Quiz quiz : quizes) {
			if(quiz.getInstructorId().equals(instructorId))
				quizList.add(quiz);
		}
		return quizList;
	}
	
	public void setQuizes(List<Quiz> quizes) {
		this.quizes = quizes;
	}

	public Student(String studentName, String email, String password, List<Quiz> quizes) {
		super();
		this.studentName = studentName;
		this.email = email;
		this.password = password;
		this.quizes = quizes;
	}

	public Student(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public Student() {
		super();
	}

	public Student(Long studentId, String studentName, String email, String password, List<Quiz> quizes) {
		super();
		this.studentId = studentId;
		this.studentName = studentName;
		this.email = email;
		this.password = password;
		this.quizes = quizes;
	}

}
