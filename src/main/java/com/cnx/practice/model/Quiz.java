package com.cnx.practice.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
public class Quiz {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long quizId;
	
	@Column
	private String QuizTitle;
	
	@Column
	private Long instructorId;
	
	@Autowired
	@ManyToMany(
			cascade = CascadeType.ALL
			)
	@JoinTable(
			name ="quiz_questions",
			
			joinColumns = @JoinColumn(
					name = "quizId",
					referencedColumnName = "quizId"
			),
			inverseJoinColumns = @JoinColumn(
					name = "questionId",
					referencedColumnName = "questionId"
			)
		)
	private List<Question> questions;
	
	
	@ManyToMany(
			cascade = CascadeType.ALL
			)
	@JoinTable(
			name ="quiz_students",
			
			joinColumns = @JoinColumn(
					name = "quizId",
					referencedColumnName = "quizId"
			),
			inverseJoinColumns = @JoinColumn(
					name = "studentId",
					referencedColumnName = "studentId"
			)
		)
	private List<Student> students;

	public Long getQuizId() {
		return quizId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	public String getQuizTitle() {
		return QuizTitle;
	}

	public void setQuizTitle(String quizTitle) {
		QuizTitle = quizTitle;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Long getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}

	public Quiz(String quizTitle, Long instructorId, List<Question> questions, List<Student> students) {
		this.QuizTitle = quizTitle;
		this.instructorId = instructorId;
		this.questions = questions;
		this.students = students;
	}

	public Quiz(Long quizId, String quizTitle, Long instructorId, List<Question> questions, List<Student> students) {
		super();
		this.quizId = quizId;
		QuizTitle = quizTitle;
		this.instructorId = instructorId;
		this.questions = questions;
		this.students = students;
	}

	public Quiz() {
		super();
	}	
	
}