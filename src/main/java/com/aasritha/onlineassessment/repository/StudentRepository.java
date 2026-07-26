package com.aasritha.onlineassessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aasritha.onlineassessment.model.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findByEmail(String email);
}
