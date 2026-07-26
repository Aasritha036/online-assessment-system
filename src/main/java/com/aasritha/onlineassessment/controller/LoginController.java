package com.aasritha.onlineassessment.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aasritha.onlineassessment.exception.UserNotFoundException;
import com.aasritha.onlineassessment.model.User;
import com.aasritha.onlineassessment.service.IUserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	IUserService userService;

	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password, HttpServletRequest req,
			Model model) {

		User user = userService.findByUserEmail(email);
		if (user != null && user.getPassword().equals(password)) {
			HttpSession session = req.getSession();
			session.setAttribute("id", user.getId());
			session.setAttribute("username", user.getUserName());
			session.setAttribute("email", user.getUserEmail());
			session.setAttribute("password", user.getPassword());
			session.setAttribute("role", user.getRole());
			if (session.getAttribute("role").equals("student")) {
				logger.info("Student logged in: " + user.getUserName());
				return "redirect:/studentHome";
			}
			logger.info("Instructor logged in: " + user.getUserName());
			return "redirect:/instructorhome";
		} else {

			if (user == null)
				throw new UserNotFoundException();
			logger.error("Invalid login attempt for user: " + email);
			model.addAttribute("error", "Invalid Password");
			return "sign-in";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}
