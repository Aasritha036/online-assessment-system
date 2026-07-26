package com.cnx.practice.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cnx.practice.model.Instructor;
import com.cnx.practice.model.Student;
import com.cnx.practice.model.User;
import com.cnx.practice.repository.InstructorRepository;
import com.cnx.practice.repository.StudentRepository;

@Service
public class UserServiceImpl implements IUserService {

	private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private InstructorRepository instructorRepository;

	@Override
	public User findByUserEmail(String userEmail) {
		logger.info("Finding user by email: " + userEmail);
		User user = new User();
		Student student = studentRepository.findByEmail(userEmail);
		if (student != null) {
			user.setId(student.getStudentId());
			user.setUserName(student.getStudentName());
			user.setPassword(student.getPassword());
			user.setUserEmail(student.getEmail());
			user.setRole("student");
			return user;
		}
		Instructor instructor = instructorRepository.findByEmail(userEmail);
		if (instructor != null) {
			user.setId(instructor.getInstructorId());
			user.setUserName(instructor.getInstructorName());
			user.setUserEmail(instructor.getEmail());
			user.setPassword(instructor.getPassword());
			user.setRole("instructor");
			return user;
		}
		logger.warn("User with email " + userEmail + " not found");
		return null;
	}

	@Override
	public void save(User user) {
		logger.info("Saving user: " + user);
		if (user.getRole().equals("student")) {
			Student student = new Student();
			student.setStudentName(user.getUserName());
			student.setEmail(user.getUserEmail());
			student.setPassword(user.getPassword());
			studentRepository.save(student);
		} else {
			Instructor instructor = new Instructor(user.getUserName(), user.getUserEmail(), user.getPassword());
			instructorRepository.save(instructor);
		}
		logger.info("User saved successfully");
	}

	@Override
	public boolean login(String email, String password) {
		
		Student student = studentRepository.findByEmail(email);
		if(student != null)
			return true;
		Instructor instructor = instructorRepository.findByEmail(email);
		if(instructor != null)
			return true;
		return false;
	}

//	public User findByUserEmail(String userEmail) {
//		User user = new User();
//		Student student = studentRepository.findByEmail(userEmail);
//		if(student != null) {
//			user.setId(student.getStudentId());
//			user.setUserName(student.getStudentName());
//			user.setPassword(student.getPassword());
//			user.setUserEmail(student.getEmail());
//			user.setRole("student");			
//			return user;
//		} 
//		Instructor instructor = instructorRepository.findByEmail(userEmail);
//		if(instructor != null) {
//			user.setId(instructor.getInstructorId());
//			user.setUserName(instructor.getInstructorName());
//			user.setUserEmail(instructor.getEmail());
//			user.setPassword(instructor.getPassword());
//			user.setRole("instructor");
//			return user;
//		}
//		return null;
//	}
//
//	public void save(User user) {
//		
//		if(user.getRole().equals("student")) {
//			Student student = new Student();
//			student.setStudentName(user.getUserName());
//			student.setEmail(user.getUserEmail());
//			student.setPassword(user.getPassword());
//			studentRepository.save(student);
//		} else {
//			Instructor instructor = new Instructor(user.getUserName(),user.getUserEmail(), user.getPassword());
//			instructorRepository.save(instructor);
//		}
//	}

}
