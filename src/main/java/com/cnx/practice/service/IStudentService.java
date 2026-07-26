package com.cnx.practice.service;

import java.util.List;

import com.cnx.practice.model.Quiz;
import com.cnx.practice.model.Student;

public interface IStudentService {

	List<Quiz> getQuizList(Long id);

	Student getById(Long id);

	List<Student> getAll();

	Quiz getByQuizIdAndStudentId(Long quizId, Long studentId);

}
