package com.aasritha.onlineassessment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aasritha.onlineassessment.model.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
	Subject findBySubjectName(String subjectName);
	List<Subject> findByInstructorId(Long instructorId);
}
