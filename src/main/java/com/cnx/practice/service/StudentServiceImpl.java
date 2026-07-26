package com.cnx.practice.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnx.practice.model.Quiz;
import com.cnx.practice.model.Student;
import com.cnx.practice.repository.StudentRepository;

@Service
public class StudentServiceImpl implements IStudentService {

	private static final Logger logger = LogManager.getLogger(StudentServiceImpl.class);

	@Autowired
	StudentRepository studentRepository;

	@Override
	public List<Quiz> getQuizList(Long id) {
		logger.info("Retrieving quiz list for student with ID: " + id);
		return null; // Implement logic to retrieve quiz list for the student
	}

	@Override
	public Student getById(Long id) {
		logger.info("Retrieving student by ID: " + id);
		return studentRepository.getById(id);
	}

	@Override
	public List<Student> getAll() {
		logger.info("Retrieving all students");
		return studentRepository.findAll();
	}

	@Override
	public Quiz getByQuizIdAndStudentId(Long quizId, Long studentId) {
		Student student = studentRepository.getById(studentId);
		for (Quiz quiz : student.getQuizes()) {
			if (quiz.getQuizId() == quizId) {
				logger.info("Retrieved quiz by ID: " + quizId + " for student with ID: " + studentId);
				return quiz;
			}
		}
		return null;
	}

//	@Override
//	public List<Quiz> getQuizList(Long id) {
//		
//		return null;
//	}
//
//	@Override
//	public Student getById(Long id) {
//		return studentRepository.getById(id);
//	}
//
//	@Override
//	public List<Student> getAll() {
//		return studentRepository.findAll();
//	}
//
//	@Override
//	public Quiz getByQuizIdAndStudentId(Long quizId, Long studentId) {
//		Student student = studentRepository.getById(studentId);
//		for(Quiz quiz : student.getQuizes()) {
//			if(quiz.getQuizId() == quizId) {
//				return quiz;
//			}
//		}
//		return null;
//	}

}
