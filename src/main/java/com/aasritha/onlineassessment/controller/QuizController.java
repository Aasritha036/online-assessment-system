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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.aasritha.onlineassessment.model.Question;
import com.aasritha.onlineassessment.model.Quiz;
import com.aasritha.onlineassessment.model.Student;
import com.aasritha.onlineassessment.model.Subject;
import com.aasritha.onlineassessment.repository.QuestionRepository;
import com.aasritha.onlineassessment.service.IQuizService;
import com.aasritha.onlineassessment.service.IStudentService;
import com.aasritha.onlineassessment.service.ISubjectService;
import com.aasritha.onlineassessment.service.ISubmissionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class QuizController {

	private static final Logger logger = LogManager.getLogger(QuizController.class);

	@Autowired
	IQuizService quizService;

	@Autowired
	IStudentService studentService;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	ISubjectService subjectService;
	
	@Autowired
	ISubmissionService submissionService;

	@GetMapping("/quiz_List")
	public String viewInstructtorQuizList(HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			List<Quiz> quizList = quizService.getAll((Long) session.getAttribute("id"));
			model.addAttribute("quizList", quizList);
			logger.info("Instructor accessed the quiz list");
			return "instructor_quiz_list";
		}
		logger.warn("Unauthorized access to quiz list page");
		return "redirect:/login";
	}

	@GetMapping("/addQuiz")
	public String showAddQuiz(Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			Quiz quiz = new Quiz();
			model.addAttribute("quiz", quiz);
			return "add_quiz";
		}
		logger.warn("Unauthorized access to add quiz page");
		return "redirect:/login";
	}

	@PostMapping("/addQuiz")
	public String addQuiz(@ModelAttribute("quiz") Quiz quiz, Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			quiz.setInstructorId((Long) session.getAttribute("id"));
			quizService.save(quiz);
			logger.info("Instructor added a new quiz");
			return "redirect:/quiz_List";
		}
		logger.warn("Unauthorized access to add quiz request");
		return "redirect:/login";
	}

	@PostMapping("/updateQuiz")
	public String updateQuiz(@ModelAttribute("quiz") Quiz quiz, Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			quiz.setInstructorId((Long) session.getAttribute("id"));
			quiz.setQuizId((Long) session.getAttribute("quizId"));
			quizService.save(quiz);
			logger.info("Instructor updated a quiz");
			return "redirect:/quiz_List";
		}
		logger.warn("Unauthorized access to update quiz request");
		return "redirect:/login";
	}

	@GetMapping("/updateQuiz/{id}")
	public String updateQuiz(@PathVariable(value = "id") Long quizId, Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			session.setAttribute("quizId", quizId);
			return "redirect:/updateQuiz";
		}
		logger.warn("Unauthorized access to update quiz page");
		return "redirect:/login";
	}

	@GetMapping("/updateQuiz")
	public String updateQuizPage(Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			Quiz quiz = quizService.getById((Long) session.getAttribute("quizId"));
			session.setAttribute("quizId", (Long) session.getAttribute("quizId"));
			model.addAttribute("quiz", quiz);
			return "update_quiz";
		}
		logger.warn("Unauthorized access to update quiz page");
		return "redirect:/login";
	}

	@GetMapping("/deleteQuiz/{id}")
	public String deleteQuiz(@PathVariable(value = "id") Long quizId, Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			quizService.deleteById(quizId);
			logger.info("Instructor deleted a quiz");
			return "redirect:/quiz_List";
		}
		logger.warn("Unauthorized access to delete quiz request");
		return "redirect:/login";
	}

	@GetMapping("/viewQuizQuestions")
	public String viewQuizQuestions(Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			Quiz quiz = quizService.getById((Long) session.getAttribute("quizId"));
			List<Question> questionList = quiz.getQuestions();
			model.addAttribute("questionList", questionList);
			logger.info("Instructor viewed quiz questions");
			return "quiz_questions";
		}
		logger.warn("Unauthorized access to view quiz questions");
		return "redirect:/login";
	}

	@GetMapping("/addQuestionToQuiz/{id}")
	public String addQuizQuestions(@PathVariable(value = "id") Long questionId, Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			quizService.addQuestions(questionId, (Long) session.getAttribute("quizId"));
			logger.info("Instructor added question to quiz");
			return "redirect:/viewQuizQuestions";
		}
		logger.warn("Unauthorized access to add question to quiz request");
		return "redirect:/login";
	}

	@GetMapping("/addStudentToQuiz/{id}")
	public String addStudentToQuiz(@PathVariable(value = "id") Long studentId, Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			quizService.addStudents(studentId, (Long) session.getAttribute("quizId"));
			logger.info("Instructor added student to quiz");
			return "redirect:/viewQuizStudents";
		}
		logger.warn("Unauthorized access to add student to quiz request");
		return "redirect:/login";
	}

	@GetMapping("/viewQuizStudents")
	public String viewQuizStudents(Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			Quiz quiz = quizService.getById((Long) session.getAttribute("quizId"));
			List<Student> studentList = quiz.getStudents();
			Map<Student, String> studentMap = new HashMap<>();
			for (Student student : studentList) {
				String percentage = (String) submissionService
						.findByStudentIdAndQuizId(student.getStudentId() ,(long) session.getAttribute("quizId"));
				studentMap.put(student, percentage);
			}
			model.addAttribute("studentMap", studentMap);
			logger.info("Instructor viewed quiz students");
			return "quiz_students";
		}
		logger.warn("Unauthorized access to view quiz students");
		return "redirect:/login";
	}

	@GetMapping("/addQuizStudent")
	public String addQuizStudent(HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			List<Student> studentList = studentService.getAll();
			model.addAttribute("studentList", studentList);
			logger.info("Instructor accessed add quiz student page");
			return "add_quiz_student";
		}
		logger.warn("Unauthorized access to add quiz student page");
		return "redirect:/login";
	}

	@GetMapping("/viewQuizDetails/{id}")
	public String viewQuizDetails(@PathVariable(value = "id") Long quizId, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			session.setAttribute("quizId", quizId);
			logger.info("Instructor viewed quiz details for quiz ID: " + quizId);
			return "redirect:/viewQuizDetails";
		}
		logger.warn("Unauthorized access to view quiz details page");
		return "redirect:/login";
	}

	@GetMapping("/viewQuizDetails")
	public String viewQuizDetails_(Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			Quiz quiz = quizService.getById((Long) session.getAttribute("quizId"));
			
			List<Student> studentList = quiz.getStudents();
			Map<Student, String> studentMap = new HashMap<>();
			for (Student student : studentList) {
				String percentage = (String) submissionService
						.findByStudentIdAndQuizId(student.getStudentId() ,(long) session.getAttribute("quizId"));
				studentMap.put(student, percentage);
			}
			model.addAttribute("studentMap", studentMap);
			logger.info("Instructor viewed quiz details");
			return "quiz_details";
		}
		logger.warn("Unauthorized access to view quiz details page");
		return "redirect:/login";
	}

	@GetMapping("/addQuizQuestion")
	public String addQuizQuestion(HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			List<Subject> subjectList = subjectService.findByInstructorId((Long) session.getAttribute("id"));
			model.addAttribute("subjectList", subjectList);
			logger.info("Instructor accessed add quiz question page");
			return "add_quiz_question";
		}
		logger.warn("Unauthorized access to add quiz question page");
		return "redirect:/login";
	}

	@GetMapping("/quizSubjectQuestions/{id}")
	public String quizSubjectQuestions(@PathVariable(value = "id") Long subjectId, Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			session.setAttribute("subjectId", subjectId);
			logger.info("Instructor viewed quiz subject questions for subject ID: " + subjectId);
			return "redirect:/quizSubjectQuestions";
		}
		logger.warn("Unauthorized access to view quiz subject questions page");
		return "redirect:/login";
	}

	@GetMapping("/quizSubjectQuestions")
	public String quizSubjectQuestion(Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			List<Question> questionList = subjectService.findBySubjectId((Long) session.getAttribute("subjectId"));
			model.addAttribute("questionsList", questionList);
			model.addAttribute("message", "Added");
			logger.info("Instructor viewed quiz subject questions");
			return "quiz_subject_questions";
		}
		logger.warn("Unauthorized access to view quiz subject questions page");
		return "redirect:/login";
	}

//	@GetMapping("/quiz_List")
//	public String viewInstructtorQuizList(HttpSession session, Model model) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			List<Quiz> quizList = quizService.getAll((Long)session.getAttribute("id"));
//			model.addAttribute("quizList", quizList);
//			return "instructor_quiz_list";
//		}			
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/addQuiz")
//	public String showAddQuiz(Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			Quiz quiz = new Quiz();
//			model.addAttribute("quiz", quiz);
//			return "add_quiz";
//		}			
//		return "redirect:/login";
//	}
//	
//	@PostMapping("/addQuiz")
//	public String addQuiz(@ModelAttribute("quiz") Quiz quiz, Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			quiz.setInstructorId((Long) session.getAttribute("id"));
//			quizService.save(quiz);
//			return "redirect:/quiz_List";
//		}			
//		return "redirect:/login";
//	}
//
//	@PostMapping("/updateQuiz")
//	public String updateQuiz(@ModelAttribute("quiz") Quiz quiz, Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			quiz.setInstructorId((Long) session.getAttribute("id"));
//			quiz.setQuizId((Long) session.getAttribute("quizId"));
//			quizService.save(quiz);
//			return "redirect:/quiz_List";
//		}			
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/updateQuiz/{id}")
//	public String updateQuiz(@PathVariable (value="id") Long quizId, Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			session.setAttribute("quizId", quizId);
//			return "redirect:/updateQuiz";
//		}			
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/updateQuiz")
//	public String updateQuizPage(Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			Quiz quiz = quizService.getById((Long) session.getAttribute("quizId"));
//			session.setAttribute("quizId", (Long) session.getAttribute("quizId"));
//			model.addAttribute("quiz", quiz);
//			return "update_quiz";
//		}			
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/deleteQuiz/{id}")
//	public String deleteQuiz(@PathVariable (value = "id") Long quizId, Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			quizService.deleteById(quizId);
//			return "redirect:/quiz_List";
//		}			
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/viewQuizQuestions")
//	public String viewQuizQuestions(Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			Quiz quiz = quizService.getById((Long) session.getAttribute("quizId"));
//			List<Question> questionList = quiz.getQuestions();
//			model.addAttribute("questionList", questionList);
//			return "quiz_questions";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/addQuizQuestion")
//	public String addQuizQuestion(HttpSession session, Model model) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//				List<Subject> subjectList = subjectService.findByInstructorId((Long) session.getAttribute("id"));
//				model.addAttribute("subjectList", subjectList);
//				System.out.println(subjectList);
//			return "add_quiz_question";
//		}
//		return "redirect:/login";
//	}
//	@GetMapping("/quizSubjectQuestions/{id}")
//	public String quizSubjectQuestions(@PathVariable (value = "id") Long subjectId, Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			session.setAttribute("subjectId", subjectId);;
//			return "redirect:/quizSubjectQuestions";
//		}
//		return "redirect:/login";
//	}
//	@GetMapping("/quizSubjectQuestions")
//	public String quizSubjectQuestion(Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			List<Question> questionList =subjectService.findBySubjectId((Long) session.getAttribute("subjectId"));
//			model.addAttribute("questionsList", questionList);
//			model.addAttribute("message", "Added");
//			System.out.println(questionList);
//			return "quiz_subject_questions";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/addQuestionToQuiz/{id}")
//	public String addQuizQuestions(@PathVariable (value = "id") Long questionId, Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			quizService.addQuestions(questionId, (Long) session.getAttribute("quizId"));
//			return "redirect:/quizSubjectQuestions";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/addStudentToQuiz/{id}")
//	public String addStudentToQuiz(@PathVariable (value = "id") Long studentId, Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			quizService.addStudents(studentId, (Long) session.getAttribute("quizId"));
//			return "redirect:/viewQuizStudents";
//		}
//		return "redirect:/login";
//	}
//	@GetMapping("/viewQuizStudents")
//	public String viewQuizStudents(Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			Quiz quiz = quizService.getById((Long) session.getAttribute("quizId"));
//			List<Student> studentList = quiz.getStudents();
//			model.addAttribute("studentList", studentList);
//			return "quiz_students";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/addQuizStudent")
//	public String addQuizStudent(HttpSession session, Model model) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			List<Student> studentList = studentService.getAll();
//			model.addAttribute("studentList", studentList);
//			return "add_quiz_student";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/viewQuizDetails/{id}")
//	public String viewQuizDetails(@PathVariable (value = "id") Long quizId, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			session.setAttribute("quizId", quizId);
//			return "redirect:/viewQuizDetails";
//		}
//		return "redirect:/login";
//	}
//	@GetMapping("/viewQuizDetails")
//	public String viewQuizDetails_(Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
////			session.setAttribute("quizId", (Long) session.getAttribute("quizId"));
//			Quiz quiz = quizService.getById((Long) session.getAttribute("quizId"));
//			List<Question> questionList = quiz.getQuestions();
//			model.addAttribute("questionList", questionList);
//			List<Student> studentList = quiz.getStudents();
//			model.addAttribute("studentList", studentList);
//			return "quiz_details";
//		}
//		return "redirect:/login";
//	}

}
