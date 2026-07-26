package com.cnx.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnx.practice.model.Submission;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
	List<Submission> findByStudentId(Long studentId);
	
	List<Submission> findByStudentIdAndQuizId(Long studentId, Long quizId);
	
	List<Submission> findByQuestionIdAndStudentIdAndQuizId(Long questionId, Long studentId, Long quizId);
}
