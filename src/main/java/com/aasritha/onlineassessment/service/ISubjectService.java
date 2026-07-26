package com.aasritha.onlineassessment.service;

import java.util.List;

import com.aasritha.onlineassessment.model.Question;
import com.aasritha.onlineassessment.model.Subject;

public interface ISubjectService {

	List<Subject> getAll(Long instructorId);

	Subject findBySubjectName(String subjectName);

	void save(Subject subject);

	List<Question> getQuestionById(long subjectId, Long instructorId);

	void saveQuestion(Question question);

	List<Subject> findByInstructorId(Long attribute);

	List<Question> findBySubjectId(Long subjectId);

	Subject getById(Long subjectId);

	void deleteSubject(Long subjectId);

	Question getQuestionByQuestionId(Long questionId);

	void deleteQuestion(Long questionId);

}
