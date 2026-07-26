package com.cnx.practice.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnx.practice.model.Question;
import com.cnx.practice.model.Submission;
import com.cnx.practice.repository.QuestionRepository;
import com.cnx.practice.repository.SubmissionRepository;

@Service
public class SubmissionServiceImpl implements ISubmissionService {

	private static final Logger logger = LogManager.getLogger(SubmissionServiceImpl.class);

	@Autowired
	SubmissionRepository submissionRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Override
	public Submission findByStudentId(Long studentId) {
		logger.info("Finding submission by student ID: " + studentId);
		List<Submission> submissions = submissionRepository.findByStudentId(studentId);
		if (submissions.isEmpty()) {
			return null;
		}
		return submissions.get(0);
	}

	@Override
	public void save(Submission submission) {
		logger.info("Saving submission: " + submission);
		submissionRepository.save(submission);
	}

	@Override
	public Submission findByStudentIdQuizId(long studentId, Long quizId) {
		logger.info("Finding submission by student ID: " + studentId + " and quiz ID: " + quizId);
		List<Submission> submissions = submissionRepository.findByStudentIdAndQuizId(studentId, quizId);
		if (submissions.isEmpty()) {
			return null;
		}
		return submissions.get(0);
	}

	@Override
	public String findByStudentIdAndQuizId(long studentId, Long quizId) {
		if(submissionRepository.findByStudentIdAndQuizId(studentId, quizId).isEmpty()) {
			return "Not Attempted";
		}
		logger.info("Calculating score percentage for student ID: " + studentId + " and quiz ID: " + quizId);
		List<Submission> submissionList = submissionRepository.findByStudentIdAndQuizId(studentId, quizId);
		double score = 0;
		double totalScore = 0;
		for (Submission submission : submissionList) {
			Question question = questionRepository.getById(submission.getQuestionId());
			if (submission.getSelectedOption().equals(question.getCorrectAnswer())) {
				score += question.getScore();
			}
			totalScore += question.getScore();
		}
		double percentage = (score / totalScore) * 100;
		return "" + percentage;
	}

	@Override
	public String getByQuestionIdAndStudentId(Long questionId, Long studentId, Long quizId) {
		logger.info("Finding submission for question ID: " + questionId + ", student ID: " + studentId
				+ ", and quiz ID: " + quizId);
		List<Submission> submissions = submissionRepository.findByQuestionIdAndStudentIdAndQuizId(questionId, studentId,
				quizId);
		if (!submissions.isEmpty()) {
			return submissions.get(0).getSelectedOption();
		}
		return "Not Attempted";
	}

//	@Override
//	public Submission findByStudentId(Long studentId) {
//		// TODO Auto-generated method stub
//		
//		if(submissionRepository.findByStudentId(studentId).isEmpty()) {
//			return null;
//		}
//		return submissionRepository.findByStudentId(studentId).get(0);
//	}

//	@Override
//	public void save(Submission submission) {
//		submissionRepository.save(submission);
//	}
//
//	@Override
//	public Submission findByStudentIdQuizId(long studentId, Long quizId) {
//		if(submissionRepository.findByStudentIdAndQuizId(studentId, quizId).isEmpty()) {
//			return null;
//		}
//		return submissionRepository.findByStudentIdAndQuizId(studentId, quizId).get(0);
//	}
//
//	@Override
//	public String findByStudentIdAndQuizId(long studentId, Long quizId) {
//		if(submissionRepository.findByStudentIdAndQuizId(studentId, quizId).isEmpty()) {
//			return "Not Attempted";
//		}
//		List<Submission> submissionList = submissionRepository.findByStudentIdAndQuizId(studentId, quizId);
//		double score = 0;
//		double totalScore = 0;
//		for(Submission submission : submissionList) {
//			Question question = questionRepository.getById(submission.getQuestionId());
//			if(submission.getSelectedOption().equals(question.getCorrectAnswer())) {
//				score += question.getScore();
//			}
//			totalScore += question.getScore();
//		}
//		double percentage = (score/totalScore) * 100;
//		return "" + percentage;
//	}
//
//	@Override
//	public String getByQuestionIdAndStudentId(Long questionId, Long studentId,Long quizId) {
//		Submission submission = submissionRepository.findByQuestionIdAndStudentIdAndQuizId(questionId, studentId, quizId).get(0);
//		if(submission != null)
//			return submission.getSelectedOption();
//		return "Not Attempted";
//	}

}
