package com.aasritha.onlineassessment.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aasritha.onlineassessment.exception.QuestionNotFoundException;
import com.aasritha.onlineassessment.model.Question;
import com.aasritha.onlineassessment.model.Quiz;
import com.aasritha.onlineassessment.model.Student;
import com.aasritha.onlineassessment.repository.QuestionRepository;
import com.aasritha.onlineassessment.repository.QuizRepository;
import com.aasritha.onlineassessment.repository.StudentRepository;

@Service
public class QuizServiceImpl implements IQuizService {

	private static final Logger logger = LogManager.getLogger(QuizServiceImpl.class);

	@Autowired
	QuizRepository quizRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	StudentRepository studentRepository;

	@Override
	public List<Quiz> getAll(Long quizId) {
		logger.info("Retrieving all quizzes for instructor with ID: " + quizId);
		return quizRepository.findByInstructorId(quizId);
	}

	@Override
	public void save(Quiz quiz) {
		quizRepository.save(quiz);
		logger.info("Saved quiz with ID: " + quiz.getQuizId());
	}

	@Override
	public Quiz getById(Long quizId) {
		logger.info("Retrieving quiz by ID: " + quizId);
		return quizRepository.getById(quizId);
	}

	@Override
	public Quiz getQuiz(Long quizId) {
		logger.info("Retrieving quiz by ID: " + quizId);
		return quizRepository.findByQuizId(quizId);
	}

	@Override
	public void addQuestions(Long questionId, long quizId) {
		Quiz quiz = quizRepository.getById(quizId);
		Question question = questionRepository.getById(questionId);
		quiz.getQuestions().add(question);
		quizRepository.save(quiz);
		logger.info("Added question with ID: " + questionId + " to quiz with ID: " + quizId);
	}

	@Override
	public void addStudents(Long studentId, Long quizId) {
		Quiz quiz = quizRepository.getById(quizId);
		if (quiz.getQuestions().isEmpty()) {
			throw new QuestionNotFoundException("The Quiz is Empty Please Add Questions Later Add Students. "
					+ "Otherwise students are not able to Take Quiz.");
		}
		Student student = studentRepository.getById(studentId);
		quiz.getStudents().add(student);
		student.getQuizes().add(quiz);
		quizRepository.save(quiz);
		studentRepository.save(student);
		logger.info("Added student with ID: " + studentId + " to quiz with ID: " + quizId);
	}

	@Override
	public void deleteById(Long quizId) {
		quizRepository.deleteById(quizId);
		logger.info("Deleted quiz with ID: " + quizId);
	}

//	@Override
//	public List<Quiz> getAll(Long quizId) {
//		return quizRepository.findByInstructorId(quizId);
//	}
//
//	@Override
//	public void save(Quiz quiz) {
//		quizRepository.save(quiz);
//	}
//
//	@Override
//	public Quiz getById(Long quizId) {
//		return quizRepository.getById(quizId);
//	}
//
//	@Override
//	public Quiz getQuiz(Long quizId) {
//		return quizRepository.findByQuizId(quizId);
//	}
//
//	@Override
//	public void addQuestions(Long questionId, long quizId) {
//		Quiz quiz = quizRepository.getById(quizId);
//		Question question = questionRepository.getById(questionId);
//		quiz.getQuestions().add(question);
//		quizRepository.save(quiz);
//	}
//
//	@Override
//	public void addStudents(Long studentId, Long quizId) {
//		Quiz quiz = quizRepository.getById(quizId);
//		if(quiz.getQuestions().isEmpty())
//			throw new QuestionNotFoundException("The Quiz is Empty Please Add Questions Later Add Students. "
//					+ "Otherwise students are not able to Take Quiz.");
//		Student student = studentRepository.getById(studentId);
//		quiz.getStudents().add(student);
//		student.getQuizes().add(quiz);
//		quizRepository.save(quiz);
//		studentRepository.save(student);
//	}
//
//	@Override
//	public void deleteById(Long quizId) {
//		quizRepository.deleteById(quizId);
//	}

}
