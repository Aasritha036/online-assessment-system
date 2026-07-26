package com.aasritha.onlineassessment.controller;

import java.util.ArrayList;
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

import com.aasritha.onlineassessment.exception.AnswerNotFoundException;
import com.aasritha.onlineassessment.model.Question;
import com.aasritha.onlineassessment.model.Quiz;
import com.aasritha.onlineassessment.model.Student;
import com.aasritha.onlineassessment.model.Submission;
import com.aasritha.onlineassessment.repository.QuestionRepository;
import com.aasritha.onlineassessment.service.IQuizService;
import com.aasritha.onlineassessment.service.IStudentService;
import com.aasritha.onlineassessment.service.ISubmissionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class StudentController {

	private static final Logger logger = LogManager.getLogger(StudentController.class);

	@Autowired
	IStudentService studentService;

	@Autowired
	IQuizService quizService;

	@Autowired
	ISubmissionService submissionService;

	@Autowired
	QuestionRepository questionRepository;

	@GetMapping("/studentHome")
	public String studentHomePage(HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("student")) {
			logger.info("Accessed student home page");
			return "student_home";
		}
		logger.warn("Unauthorized access attempt to student home page");
		return "redirect:/login";
	}

	@GetMapping("/quizList")
	public String viewStudentQuizListPage(HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("student")) {
			Student student = studentService.getById((Long) session.getAttribute("id"));
			List<Quiz> quizList = student.getQuizes();
			List<Quiz> quizListNew = new ArrayList<Quiz>();
			for (Quiz quiz : quizList) {
				Submission sub = submissionService.findByStudentIdQuizId((long) session.getAttribute("id"),
						quiz.getQuizId());
				if (sub == null) {
					quizListNew.add(quiz);
				}
			}
			if (quizListNew.isEmpty()) {
				logger.info("No available quizzes for the student");
				return "quiz_list_empty";
			}
			model.addAttribute("quizList", quizListNew);
			logger.info("Viewed student quiz list page");
			return "quiz_list";
		}
		logger.warn("Unauthorized access attempt to student quiz list page");
		return "redirect:/login";
	}

	@GetMapping("/performance")
	public String viewPerformancePage(Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("student")) {
			Student student = studentService.getById((Long) session.getAttribute("id"));
			List<Quiz> quizList = student.getQuizes();
			Map<Quiz, String> quizMap = new HashMap<>();
			for (Quiz quiz : quizList) {
				String percentage = (String) submissionService
						.findByStudentIdAndQuizId((long) session.getAttribute("id"), quiz.getQuizId());
				if (percentage.equals("Not Attempted")) {

				} else
					quizMap.put(quiz, percentage);
			}
			model.addAttribute("quizMap", quizMap);
			logger.info("Viewed student performance page");
			return "performance";
		}
		logger.warn("Unauthorized access attempt to student performance page");
		return "redirect:/login";
	}

	@GetMapping("/viewPaper/{id}")
	public String viewQuizPage(@PathVariable(value = "id") Long quizId, Model model, HttpSession session) {
		if (session.getAttribute("role") != null && (session.getAttribute("role").equals("student") || session.getAttribute("role").equals("instructor"))) {
			session.setAttribute("quizId", quizId);
			logger.info("Viewed paper for quiz ID: " + quizId);
			return "redirect:/viewPaper";
		}
		logger.warn("Unauthorized access attempt to view paper for quiz ID: " + quizId);
		return "redirect:/login";
	}

	@GetMapping("/viewPaper")
	public String viewPaper(HttpSession session, Model model) {
		if (session.getAttribute("role") != null && (session.getAttribute("role").equals("student") || session.getAttribute("role").equals("instructor"))) {
			if(session.getAttribute("role").equals("student")) {
				Quiz quiz = studentService.getByQuizIdAndStudentId((Long) session.getAttribute("quizId"),
						(Long) session.getAttribute("id"));
				Map<Question, String> questionMap = new HashMap<>();
				List<Question> questions = quiz.getQuestions();
				for (Question question : questions) {
					String selectedOption = submissionService.getByQuestionIdAndStudentId(question.getQuestionId(),
							(Long) session.getAttribute("id"), quiz.getQuizId());
					questionMap.put(question, selectedOption);
				}
				model.addAttribute("questionMap", questionMap);
				logger.info("Viewed paper details");
				return "view_answer_paper";
			}
			Quiz quiz = studentService.getByQuizIdAndStudentId((Long) session.getAttribute("quizId"),
					(Long) session.getAttribute("studentId"));
			Map<Question, String> questionMap = new HashMap<>();
			List<Question> questions = quiz.getQuestions();
			for (Question question : questions) {
				String selectedOption = submissionService.getByQuestionIdAndStudentId(question.getQuestionId(),
						(Long) session.getAttribute("studentId"), quiz.getQuizId());
				questionMap.put(question, selectedOption);
			}
			model.addAttribute("questionMap", questionMap);
			logger.info("Viewed paper details");
			String percentage = (String) submissionService
					.findByStudentIdAndQuizId((long) session.getAttribute("studentId"), quiz.getQuizId());
			if(percentage.equalsIgnoreCase("Not Attempted"))
				return "exam_notAttempted";
			return "view_student_paper";
		}
		logger.warn("Unauthorized access attempt to view paper");
		return "redirect:/login";

	}

	@GetMapping("/startQuiz/{id}")
	public String startQuiz(@PathVariable(value = "id") Long quizId, Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("student")) {
			session.setAttribute("quizId", quizId);
			Quiz quiz = quizService.getQuiz(quizId);
			List<Question> questions = quiz.getQuestions();
			model.addAttribute("question", questions.get(0));
			session.setAttribute("questionId", questions.get(0).getQuestionId());
			session.setAttribute("questions", questions);
			session.setAttribute("i", 0);
			session.setAttribute("score", 0);
			session.setAttribute("totalScore", questions.get(0).getScore());
			logger.info("Started quiz with ID: " + quizId);
			return "start_quiz";
		}
		logger.warn("Unauthorized access attempt to start quiz with ID: " + quizId);
		return "redirect:/login";
	}

	@GetMapping("/submitQuiz")
	public String submitQuiz(@ModelAttribute Submission submission, Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("student")) {
			if (submission.getSelectedOption() == null)
				throw new AnswerNotFoundException();
			Long questionId = (Long) session.getAttribute("questionId");
			Question question = questionRepository.getById(questionId);
			submission.setQuestionId(questionId);
			submission.setQuizId((Long) session.getAttribute("quizId"));
			submission.setStudentId((Long) session.getAttribute("id"));
			if (submission.getSelectedOption().equals(question.getCorrectAnswer())) {
				int score = (int) session.getAttribute("score");
				score += question.getScore();
				session.setAttribute("score", score);
			}
			System.out.println(session.getAttribute("score"));
			submissionService.save(submission);
			List<Question> questions = (List<Question>) session.getAttribute("questions");
			int i = (int) session.getAttribute("i");
			if (questions.size() != i + 1) {
				session.setAttribute("questionId", questions.get(i + 1).getQuestionId());
				int totalScore = (int) session.getAttribute("totalScore");
				session.setAttribute("totalScore", totalScore + questions.get(i + 1).getScore());
				model.addAttribute("question", questions.get(i + 1));
				session.setAttribute("i", i + 1);
				return "start_quiz";
			}
			model.addAttribute("score", session.getAttribute("score"));
			model.addAttribute("totalScore", session.getAttribute("totalScore"));
			logger.info("Submitted quiz");
			return "quiz_score";
		}
		logger.warn("Unauthorized access attempt to submit quiz");
		return "redirect:/login";
	}

//	@GetMapping("/studentHome")
//	public String studentHomePage(HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("student"))
//			return "student_home";
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/quizList")
//	public String viewStudentQuizListPage(HttpSession session, Model model) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("student")) {
//			Student student = studentService.getById((Long) session.getAttribute("id"));
//			List<Quiz> quizList = student.getQuizes();
//			List<Quiz> quizListNew = new ArrayList<Quiz>();
//			for(Quiz quiz: quizList) {
//				Submission sub = submissionService.findByStudentIdQuizId((long) session.getAttribute("id"), quiz.getQuizId());
//				if(sub == null) {
//					quizListNew.add(quiz);
//				}
//			}if(quizListNew.isEmpty()) {
//				return "quiz_list_empty";
//			}
//			model.addAttribute("quizList", quizListNew);
//			return "quiz_list";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/performance")
//	public String viewPerformancePage(Model model,HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("student")) {
//			Student student = studentService.getById((Long) session.getAttribute("id"));
//			List<Quiz> quizList = student.getQuizes();
//			Map<Quiz, String> quizMap = new HashMap<>();
//			for(Quiz quiz: quizList) {
//				String percentage = (String) submissionService.findByStudentIdAndQuizId((long) session.getAttribute("id"), quiz.getQuizId());
//				if(percentage.equals("Not Attempted")) {
//					
//				}
//				else
//					quizMap.put(quiz, percentage);
//			}
//			model.addAttribute("quizMap", quizMap);
//			return "performance";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/viewPaper/{id}")
//	public String viewQuizPage(@PathVariable (value = "id") Long quizId, Model model,HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("student")) {
//			session.setAttribute("quizId", quizId);
//			return "redirect:/viewPaper";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/viewPaper")
//	public String viewPaper(HttpSession session, Model model) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("student")) {
//			Quiz quiz = studentService.getByQuizIdAndStudentId((Long) session.getAttribute("quizId"), (Long) session.getAttribute("id"));
//			Map<Question, String> questionMap = new HashMap<>();
//			List<Question> questions = quiz.getQuestions();
//			for(Question question : questions) {
//				String selectedOption = submissionService.getByQuestionIdAndStudentId(question.getQuestionId(), (Long) session.getAttribute("id"), quiz.getQuizId());
//				questionMap.put(question, selectedOption);
//			}
//			model.addAttribute("questionMap", questionMap);
//			return "view_answer_paper";
//		}
//		return "redirect:/login";
//		
//	}
//	
//	@GetMapping("/startQuiz/{id}")
//	public String startQuiz(@PathVariable (value = "id") Long quizId, Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("student")) {
//			session.setAttribute("quizId", quizId);
//			Quiz quiz = quizService.getQuiz(quizId);
//			List<Question> questions = quiz.getQuestions();
//			model.addAttribute("question", questions.get(0));
//			session.setAttribute("questionId", questions.get(0).getQuestionId());
//			session.setAttribute("questions", questions);
//			session.setAttribute("i", 0);
//			session.setAttribute("score", 0);
//			session.setAttribute("totalScore",questions.get(0).getScore());
//			return "start_quiz";
//		}
//			
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/submitQuiz")
//	public String submitQuiz(@ModelAttribute Submission submission, Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("student")) {
//			if(submission.getSelectedOption() == null)
//				throw new AnswerNotFoundException();
//			Long questionId = (Long) session.getAttribute("questionId");
//			Question question = questionRepository.getById(questionId);
//			submission.setQuestionId(questionId);
//			submission.setQuizId((Long) session.getAttribute("quizId"));
//			submission.setStudentId((Long) session.getAttribute("id"));
//			if(submission.getSelectedOption().equals(question.getCorrectAnswer())) {
//				int score = (int) session.getAttribute("score");
//				score += question.getScore();
//				session.setAttribute("score", score);
//			}
//			System.out.println(session.getAttribute("score"));			
//			submissionService.save(submission);
//			List<Question> questions = (List<Question>) session.getAttribute("questions");
//			int i = (int) session.getAttribute("i");
//			if(questions.size() != i+1) {
//				session.setAttribute("questionId", questions.get(i+1).getQuestionId());	
//				int totalScore = (int)session.getAttribute("totalScore");
//				session.setAttribute("totalScore", totalScore + questions.get(i+1).getScore());
//				model.addAttribute("question", questions.get(i+1));
//				session.setAttribute("i", i+1);
//				return "start_quiz";
//			}
//			model.addAttribute("score", session.getAttribute("score"));
//			model.addAttribute("totalScore", session.getAttribute("totalScore"));
//			return "quiz_score";
//		}
//			
//		return "redirect:/login";
//	}

}
