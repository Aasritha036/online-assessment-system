package com.aasritha.onlineassessment.model;

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
public class Question {

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionTitle=" + questionTitle + ", option1=" + option1
				+ ", option2=" + option2 + ", option3=" + option3 + ", option4=" + option4 + ", correctAnswer="
				+ correctAnswer + ", difficultyLevel=" + difficultyLevel + ", subjectId=" + subjectId + ", score="
				+ score + ", instructorId=" + instructorId + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionId;
	
	@Column(length = 100)
	private String questionTitle;
	
	@Column(length = 40)
	private String option1;
	
	@Column(length = 40)
	private String option2;
	
	@Column(length = 40)
	private String option3;
	
	@Column(length = 40)
	private String option4;
	
	@Column(length = 40)
	private String correctAnswer;
	
	@Column(length = 40)
	private String difficultyLevel;
	
	@Column
	private Long subjectId;
	
	@Column
	private int score;
	
	@Column
	private Long instructorId;
	
	@ManyToMany(mappedBy = "questions", cascade = { CascadeType.ALL })
    private List<Quiz> quizes;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getOption1() {
		return option1;
	}

	public void setOption1(String option1) {
		this.option1 = option1;
	}

	public String getOption2() {
		return option2;
	}

	public void setOption2(String option2) {
		this.option2 = option2;
	}

	public String getOption3() {
		return option3;
	}

	public void setOption3(String option3) {
		this.option3 = option3;
	}

	public String getOption4() {
		return option4;
	}

	public void setOption4(String option4) {
		this.option4 = option4;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctOption) {
		this.correctAnswer = correctOption;
	}

	public String getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Question(String questionTitle, String option1, String option2, String option3, String option4,
			String correctAnswer, String difficultyLevel, Long subjectId) {
		super();
		this.questionTitle = questionTitle;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.correctAnswer = correctAnswer;
		this.difficultyLevel = difficultyLevel;
		this.subjectId = subjectId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Long getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(Long instructorId) {
		this.instructorId = instructorId;
	}

	public List<Quiz> getQuizes() {
		return quizes;
	}

	public void setQuizes(List<Quiz> quizes) {
		this.quizes = quizes;
	}

	public Question(Long questionId, String questionTitle, String option1, String option2, String option3,
			String option4, String correctAnswer, String difficultyLevel, Long subjectId, int score, Long instructorId,
			List<Quiz> quizes) {
		super();
		this.questionId = questionId;
		this.questionTitle = questionTitle;
		this.option1 = option1;
		this.option2 = option2;
		this.option3 = option3;
		this.option4 = option4;
		this.correctAnswer = correctAnswer;
		this.difficultyLevel = difficultyLevel;
		this.subjectId = subjectId;
		this.score = score;
		this.instructorId = instructorId;
		this.quizes = quizes;
	}

	public Question() {
		super();
	}
	
}