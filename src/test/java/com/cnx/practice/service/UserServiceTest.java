package com.cnx.practice.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import com.cnx.practice.model.Instructor;
import com.cnx.practice.model.Student;
import com.cnx.practice.model.User;
import com.cnx.practice.repository.InstructorRepository;
import com.cnx.practice.repository.StudentRepository;

@RunWith(SpringRunner.class)
public class UserServiceTest {
	
	@Mock
	private StudentRepository studentRepository;

	@Mock
	private InstructorRepository instructorRepository;
	
	@InjectMocks
	private IUserService userService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testLogin() {
		Student existingUser = new Student("email", "password");
		
		when(studentRepository.findByEmail("email")).thenReturn(existingUser);
		
		Instructor existingUserI = new Instructor("email", "password");
		
		when(instructorRepository.findByEmail("email")).thenReturn(existingUserI);
		
		boolean isLoggedIn = userService.login("email", "password");
		
		assertEquals(true, isLoggedIn);
		
	}
	
}
