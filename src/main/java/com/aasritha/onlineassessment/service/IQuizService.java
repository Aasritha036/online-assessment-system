package com.aasritha.onlineassessment.service;

import java.util.List;

import com.aasritha.onlineassessment.model.Quiz;

public interface IQuizService {

	public List<Quiz> getAll(Long quizId);

	public void save(Quiz quiz);

	public Quiz getById(Long quizId);

	public Quiz getQuiz(Long attribute);

	public void addQuestions(Long questionId, long quizId);

	public void addStudents(Long studentId, Long attribute);

	public void deleteById(Long quizId);

}
