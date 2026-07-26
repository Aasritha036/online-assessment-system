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
public class Submission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long submissionId;
	
	@Column
	private Long quizId;
	
	@Column
	private Long questionId;
	
	@Column
	private Long studentId;
	
	@Column
	private String selectedOption;

	public Long getQuizId() {
		return quizId;
	}

	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getSelectedOption() {
		return selectedOption;
	}

	public void setSelectedOption(String selectedOption) {
		this.selectedOption = selectedOption;
	}

	public Long getSubmissionId() {
		return submissionId;
	}

	public void setSubmissionId(Long submissionId) {
		this.submissionId = submissionId;
	}

	public Submission(Long quizId, Long questionId, Long studentId, String selectedOption) {
		super();
		this.quizId = quizId;
		this.questionId = questionId;
		this.studentId = studentId;
		this.selectedOption = selectedOption;
	}

	public Submission() {
		super();
	}

	public Submission(Long submissionId, Long quizId, Long questionId, Long studentId, String selectedOption) {
		super();
		this.submissionId = submissionId;
		this.quizId = quizId;
		this.questionId = questionId;
		this.studentId = studentId;
		this.selectedOption = selectedOption;
	}

}