package com.aasritha.onlineassessment.controller;

import java.util.List;

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
import com.aasritha.onlineassessment.model.Subject;
import com.aasritha.onlineassessment.service.ISubjectService;

import jakarta.servlet.http.HttpSession;

@Controller
public class SubjectController {

	private static final Logger logger = LogManager.getLogger(SubjectController.class);

	@Autowired
	ISubjectService subjectService;

	@GetMapping("/subject")
	public String viewSubjectPage(HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			List<Subject> subjectList = subjectService.getAll((Long) session.getAttribute("id"));
			model.addAttribute("subjectList", subjectList);
			logger.info("Viewed subject page");
			return "subject";
		}
		logger.warn("Unauthorized access attempt to view subject page");
		return "redirect:/login";
	}

	@GetMapping("/addSubject")
	public String addSubjectPage(HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			Subject subject = new Subject();
			model.addAttribute("subject", subject);
			logger.info("Viewed add subject page");
			return "add_subject";
		}
		logger.warn("Unauthorized access attempt to view add subject page");
		return "redirect:/login";
	}

	@GetMapping("/updateSubject/{id}")
	public String updateSubject(@PathVariable(value = "id") Long subjectId, HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			session.setAttribute("subjectId", subjectId);
			logger.info("Redirected to update subject with ID: " + subjectId);
			return "redirect:/updateSubject";
		}
		logger.warn("Unauthorized access attempt to update subject with ID: " + subjectId);
		return "redirect:/login";
	}

	@GetMapping("/updateSubject")
	public String updateSubject(HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			Subject subject = subjectService.getById((Long) session.getAttribute("subjectId"));
			model.addAttribute("subject", subject);
			logger.info("Viewed update subject page");
			return "update_subject";
		}
		logger.warn("Unauthorized access attempt to view update subject page");
		return "redirect:/login";
	}

	@PostMapping("/updateSubject")
	public String updateSubject(@ModelAttribute Subject subject, HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			subject.setSubjectId((Long) session.getAttribute("subjectId"));
			subjectService.save(subject);
			logger.info("Updated subject with ID: " + subject.getSubjectId());
			return "redirect:/subject";
		}
		logger.warn("Unauthorized access attempt to update subject");
		return "redirect:/login";
	}

	@GetMapping("/deleteSubject/{id}")
	public String deleteSubject(@PathVariable(value = "id") Long subjectId, HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			subjectService.deleteSubject(subjectId);
			logger.info("Deleted subject with ID: " + subjectId);
			return "redirect:/subject";
		}
		logger.warn("Unauthorized access attempt to delete subject with ID: " + subjectId);
		return "redirect:/login";
	}

	@PostMapping("/saveSubject")
	public String saveSubject(@ModelAttribute("subject") Subject subject, HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			Subject existingSubject = subjectService.findBySubjectName(subject.getSubjectName());
			if (existingSubject != null) {
				model.addAttribute("error", "Already this subject is available");
				return "redirect:/addSubject";
			}
			subject.setInstructorId((Long) session.getAttribute("id"));
			subjectService.save(subject);
			logger.info("Saved new subject: " + subject.getSubjectName());
			return "redirect:/subject";
		}
		logger.warn("Unauthorized access attempt to save new subject");
		return "redirect:/login";
	}

	@GetMapping("/viewQuestions/{id}")
	public String viewQuestionPage(@PathVariable(value = "id") long subjectId, Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			session.setAttribute("subjectId", subjectId);
			logger.info("Redirected to view questions for subject ID: " + subjectId);
			return "redirect:/viewQuestions";
		}
		logger.warn("Unauthorized access attempt to view questions for subject ID: " + subjectId);
		return "redirect:/login";
	}

	@GetMapping("/addQuestion")
	public String addQuestionPage(Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			Question question = new Question();
			model.addAttribute("question", question);
			logger.info("Viewed add question page");
			return "add_question";
		}
		logger.warn("Unauthorized access attempt to view add question page");
		return "redirect:/login";
	}

	@PostMapping("/saveQuestion")
	public String saveQuestion(@ModelAttribute("question") Question question, HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			question.setInstructorId((Long) session.getAttribute("id"));
			question.setSubjectId((Long) session.getAttribute("subjectId"));
			subjectService.saveQuestion(question);
			logger.info("Saved new question for subject ID: " + question.getSubjectId());
			return "redirect:/viewQuestions";
		}
		logger.warn("Unauthorized access attempt to save new question");
		return "redirect:/login";
	}

	@GetMapping("/updateQuestion/{id}")
	public String updateQuestionPage(@PathVariable(value = "id") Long questionId, Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			session.setAttribute("questionId", questionId);
			logger.info("Redirected to update question with ID: " + questionId);
			return "redirect:/updateQuestion";
		}
		logger.warn("Unauthorized access attempt to update question with ID: " + questionId);
		return "redirect:/login";
	}

	@GetMapping("/updateQuestion")
	public String updateQuestionPage(Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			Question question = subjectService.getQuestionByQuestionId((Long) session.getAttribute("questionId"));
			model.addAttribute("question", question);
			logger.info("Viewed update question page");
			return "update_question";
		}
		logger.warn("Unauthorized access attempt to view update question page");
		return "redirect:/login";
	}

	@PostMapping("/updateQuestion")
	public String updateQuestion(@ModelAttribute("question") Question question, HttpSession session, Model model) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			question.setQuestionId((Long) session.getAttribute("questionId"));
			question.setInstructorId((Long) session.getAttribute("id"));
			question.setSubjectId((Long) session.getAttribute("subjectId"));
			subjectService.saveQuestion(question);
			logger.info("Updated question with ID: " + question.getQuestionId());
			return "redirect:/viewQuestions";
		}
		logger.warn("Unauthorized access attempt to update question");
		return "redirect:/login";
	}

	@GetMapping("/deleteQuestion/{id}")
	public String deleteQuestion(@PathVariable(value = "id") Long questionId, Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			subjectService.deleteQuestion(questionId);
			logger.info("Deleted question with ID: " + questionId);
			return "redirect:/viewQuestions";
		}
		logger.warn("Unauthorized access attempt to delete question with ID: " + questionId);
		return "redirect:/login";
	}

	@GetMapping("/viewQuestions")
	public String viewQuestionBank(Model model, HttpSession session) {
		if (session.getAttribute("role") != null && session.getAttribute("role").equals("instructor")) {
			List<Question> questionsList = subjectService.getQuestionById((Long) session.getAttribute("subjectId"),
					(Long) session.getAttribute("id"));
			model.addAttribute("questionsList", questionsList);
			logger.info("Viewed question bank");
			return "question";
		}
		logger.warn("Unauthorized access attempt to view question bank");
		return "redirect:/login";
	}

//	@GetMapping("/subject")
//	public String viewSubjectPage(HttpSession session, Model model) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			List<Subject> subjectList = subjectService.getAll((Long) session.getAttribute("id"));
//			model.addAttribute("subjectList", subjectList);
//			return "subject";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/addSubject")
//	public String addSubjectPage(HttpSession session, Model model) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			Subject subject = new Subject();
//			model.addAttribute("subject", subject);
//			return "add_subject";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/updateSubject/{id}")
//	public String updateSubject(@PathVariable (value="id") Long subjectId, HttpSession session, Model model) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			session.setAttribute("subjectId", subjectId);
//			return "redirect:/updateSubject";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/updateSubject")
//	public String updateSubject(HttpSession session, Model model) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			Subject subject = subjectService.getById((Long) session.getAttribute("subjectId"));
//			model.addAttribute("subject", subject);
//			return "update_subject";
//		}
//		return "redirect:/login";
//	}
//	
//	@PostMapping("/updateSubject")
//	public String updateSubject(@ModelAttribute Subject subject, HttpSession session, Model model) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			subject.setSubjectId( (Long) session.getAttribute("subjectId"));
//			subjectService.save(subject);
//			return "redirect:/subject";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/deleteSubject/{id}")
//		public String deleteSubject(@PathVariable (value="id") Long subjectId, HttpSession session, Model model) {
//			if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//				subjectService.deleteSubject(subjectId);
//				return "redirect:/subject";
//			}
//		return "redirect:/login";
//	}
//	
//	@PostMapping("/saveSubject")
//	public String saveSubject(@ModelAttribute("subject") Subject subject,HttpSession session, Model model) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			Subject existingSubject = subjectService.findBySubjectName(subject.getSubjectName());
//			if(existingSubject != null) {
//				model.addAttribute("error", "Already this subject is available");
//				return "redirect:/addSubject";
//			}
//			subject.setInstructorId((Long) session.getAttribute("id"));
//			subjectService.save(subject);
//			return "redirect:/subject";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/viewQuestions/{id}")
//	public String viewQuestionPage(@PathVariable ( value = "id") long subjectId, Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			session.setAttribute("subjectId", subjectId);
//			return "redirect:/viewQuestions";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/addQuestion")
//	public String addQuestionPage(Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			Question question = new Question();
//			model.addAttribute("question", question);
//			return "add_question";
//		}
//		return "redirect:/login";
//	}
//
//	@PostMapping("/saveQuestion")
//	public String saveQuestion(@ModelAttribute("question") Question question,HttpSession session, Model model) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			question.setInstructorId((Long) session.getAttribute("id"));
//			question.setSubjectId((Long) session.getAttribute("subjectId"));
//			subjectService.saveQuestion(question);
//			return "redirect:/viewQuestions";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/updateQuestion/{id}")
//	public String updateQuestionPage(@PathVariable (value="id") Long questionId, Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			session.setAttribute("questionId", questionId);
//			return "redirect:/updateQuestion";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/updateQuestion")
//	public String updateQuestionPage(Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			Question question = subjectService.getQuestionByQuestionId((Long) session.getAttribute("questionId"));
//			model.addAttribute("question", question);
//			return "update_question";
//		}
//		return "redirect:/login";
//	}
//
//	@PostMapping("/updateQuestion")
//	public String updateQuestion(@ModelAttribute("question") Question question,HttpSession session, Model model) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			question.setQuestionId((Long) session.getAttribute("questionId"));
//			question.setInstructorId((Long) session.getAttribute("id"));
//			question.setSubjectId((Long) session.getAttribute("subjectId"));
//			subjectService.saveQuestion(question);
//			return "redirect:/viewQuestions";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/deleteQuestion/{id}")
//	public String deleteQuestion(@PathVariable (value="id") Long questionId, Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			subjectService.deleteQuestion(questionId);
//			return "redirect:/viewQuestions";
//		}
//		return "redirect:/login";
//	}
//	
//	@GetMapping("/viewQuestions")
//	public String viewQuestionBank(Model model, HttpSession session) {
//		if(session.getAttribute("role")!=null && session.getAttribute("role").equals("instructor")) {
//			List<Question> questionsList = subjectService.getQuestionById((Long) session.getAttribute("subjectId"), (Long) session.getAttribute("id"));
//			model.addAttribute("questionsList", questionsList);
//			return "question";
//		}
//		return "redirect:/login";
//	}

}
