package com.aasritha.onlineassessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aasritha.onlineassessment.model.Question;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	List<Question> findBySubjectIdAndInstructorId(Long subjectId, Long instructorId);
	
	List<Question> findBySubjectId(Long subjectId);

}
