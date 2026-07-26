package com.cnx.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnx.practice.model.Student;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
	Student findByEmail(String email);
}
