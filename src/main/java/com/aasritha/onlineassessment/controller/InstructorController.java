package com.aasritha.onlineassessment.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.aasritha.onlineassessment.model.Quiz;
import com.aasritha.onlineassessment.model.Student;
import com.aasritha.onlineassessment.model.Submission;
import com.aasritha.onlineassessment.service.IStudentService;
import com.aasritha.onlineassessment.service.ISubmissionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class InstructorController {

	private static final Logger logger = LogManager.getLogger(InstructorController.class);

	@Autowired
	IStudentService studentService;

	@Autowired
	ISubmissionService submissionService;

	@GetMapping("/instructorhome")
	public String instructorHomePage(HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			logger.info("Instructor accessed home page");
			return "instructor_homepage";
		}
		logger.warn("Unauthorized access to instructor home page");
		return "redirect:/login";
	}

	@GetMapping("/studentsList")
	public String viewStudentsListPage(HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			List<Student> studentsList = studentService.getAll();
			model.addAttribute("studentsList", studentsList);
			logger.info("Instructor viewed the list of students");
			return "students_list";
		}
		logger.warn("Unauthorized access to students list page");
		return "redirect:/login";
	}

	@GetMapping("/showStudentPerformance/{id}")
	public String showStudentPerformance(@PathVariable(value = "id") Long studentId, HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			session.setAttribute("studentId", studentId);
			logger.info("Instructor accessed performance of student with ID: " + studentId);
			return "redirect:/showPerformance";
		}
		logger.warn("Unauthorized access to student performance page");
		return "redirect:/login";
	}

	@GetMapping("/quizNotAttempted")
	public String quizNotAttempted(HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			Student student = studentService.getById((Long) session.getAttribute("studentId"));
			List<Quiz> quizList = student.getQuizes();
			model.addAttribute("quizList", quizList);
			logger.info("Instructor viewed quizzes not attempted by student");
			return "quiz_not_attempted";
		}
		logger.warn("Unauthorized access to quizzes not attempted page");
		return "redirect:/login";
	}

	@GetMapping("/showPerformance")
	public String viewPerformancePage(Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			Student student = studentService.getById((Long) session.getAttribute("studentId"));
			List<Quiz> quizList = student.getQuizes((Long) session.getAttribute("id"));
			Map<Quiz, String> quizMap = new HashMap<>();
			for (Quiz quiz : quizList) {
				String percentage = (String) submissionService
						.findByStudentIdAndQuizId((long) session.getAttribute("studentId"), quiz.getQuizId());
				quizMap.put(quiz, percentage);
			}
			model.addAttribute("quizMap", quizMap);
			logger.info("Instructor viewed performance of student with ID: " + session.getAttribute("studentId"));
			return "student_performance";
		}
		logger.warn("Unauthorized access to student performance page");
		return "redirect:/login";
	}

}
