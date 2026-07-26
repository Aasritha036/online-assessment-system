package com.cnx.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnx.practice.model.Quiz;

import java.util.List;


@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
	
	List<Quiz> findByInstructorId(Long instructorId);
	
	Quiz findByQuizId(Long quizId);
	
	

}
