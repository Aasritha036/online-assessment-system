package com.aasritha.onlineassessment.service;

import java.util.List;

import com.aasritha.onlineassessment.model.Quiz;
import com.aasritha.onlineassessment.model.Student;

public interface IStudentService {

	List<Quiz> getQuizList(Long id);

	Student getById(Long id);

	List<Student> getAll();

	Quiz getByQuizIdAndStudentId(Long quizId, Long studentId);

}
