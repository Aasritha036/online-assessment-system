package com.cnx.practice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cnx.practice.model.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {
	Instructor findByEmail(String email);;
}
