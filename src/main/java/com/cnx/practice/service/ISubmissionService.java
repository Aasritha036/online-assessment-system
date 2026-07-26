package com.cnx.practice.service;

import com.cnx.practice.model.Submission;

public interface ISubmissionService {

	Submission findByStudentId(Long studentId);

	void save(Submission submission);

	Submission findByStudentIdQuizId(long studentId, Long quizId);
	
	String findByStudentIdAndQuizId(long studentId, Long quizId);

	String getByQuestionIdAndStudentId(Long questionId, Long studentId, Long quizId);

}
