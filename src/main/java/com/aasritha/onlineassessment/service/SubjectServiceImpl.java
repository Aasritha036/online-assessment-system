package com.aasritha.onlineassessment.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aasritha.onlineassessment.model.Question;
import com.aasritha.onlineassessment.model.Subject;
import com.aasritha.onlineassessment.repository.QuestionRepository;
import com.aasritha.onlineassessment.repository.SubjectRepository;

@Service
public class SubjectServiceImpl implements ISubjectService {

	private static final Logger logger = LogManager.getLogger(SubjectServiceImpl.class);

	@Autowired
	SubjectRepository subjectRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Override
	public List<Subject> getAll(Long instructorId) {
		logger.info("Retrieving all subjects for instructor ID: " + instructorId);
		return subjectRepository.findByInstructorId(instructorId);
	}

	@Override
	public Subject findBySubjectName(String subjectName) {
		logger.info("Finding subject by name: " + subjectName);
		return subjectRepository.findBySubjectName(subjectName);
	}

	@Override
	public void save(Subject subject) {
		logger.info("Saving subject: " + subject);
		subjectRepository.save(subject);
	}

	@Override
	public List<Question> getQuestionById(long subjectId, Long instructorId) {
		logger.info("Retrieving questions by subject ID: " + subjectId + " and instructor ID: " + instructorId);
		return questionRepository.findBySubjectIdAndInstructorId(subjectId, instructorId);
	}

	@Override
	public void saveQuestion(Question question) {
		logger.info("Saving question: " + question);
		questionRepository.save(question);
	}

	@Override
	public List<Subject> findByInstructorId(Long instructorId) {
		logger.info("Finding subjects by instructor ID: " + instructorId);
		return subjectRepository.findByInstructorId(instructorId);
	}

	@Override
	public List<Question> findBySubjectId(Long subjectId) {
		logger.info("Finding questions by subject ID: " + subjectId);
		return questionRepository.findBySubjectId(subjectId);
	}

	@Override
	public Subject getById(Long subjectId) {
		logger.info("Retrieving subject by ID: " + subjectId);
		return subjectRepository.getById(subjectId);
	}

	@Override
	public void deleteSubject(Long subjectId) {
		logger.info("Deleting subject by ID: " + subjectId);
		subjectRepository.deleteById(subjectId);
	}

	@Override
	public Question getQuestionByQuestionId(Long questionId) {
		logger.info("Retrieving question by ID: " + questionId);
		return questionRepository.getById(questionId);
	}

	@Override
	public void deleteQuestion(Long questionId) {
		logger.info("Deleting question by ID: " + questionId);
		questionRepository.deleteById(questionId);
	}

//	@Override
//	public List<Subject> getAll(Long instructorId) {
//		return subjectRepository.findByInstructorId(instructorId);
//	}
//
//	@Override
//	public Subject findBySubjectName(String subjectName) {
//		return subjectRepository.findBySubjectName(subjectName);
//	}
//
//	@Override
//	public void save(Subject subject) {
//		subjectRepository.save(subject);
//	}
//
//	@Override
//	public List<Question> getQuestionById(long subjectId, Long instructorId) {
//		return questionRespository.findBySubjectIdAndInstructorId(subjectId, instructorId);
//	}
//
//	@Override
//	public void saveQuestion(Question question) {
//		questionRespository.save(question);
//	}
//
//	@Override
//	public List<Subject> findByInstructorId(Long instructorId) {
//		// TODO Auto-generated method stub
//		return subjectRepository.findByInstructorId(instructorId);
//	}
//
//	@Override
//	public List<Question> findBySubjectId(Long subjectId) {
//		return questionRespository.findBySubjectId(subjectId);
//	}
//
//	@Override
//	public Subject getById(Long subjectId) {
//		return subjectRepository.getById(subjectId);
//	}
//
//	@Override
//	public void deleteSubject(Long subjectId) {
//		subjectRepository.deleteById(subjectId);
//	}
//
//	@Override
//	public Question getQuestionByQuestionId(Long questionId) {
//		return questionRespository.getById(questionId);
//	}
//
//	@Override
//	public void deleteQuestion(Long questionId) {
//		questionRespository.deleteById(questionId);
//	}

}
