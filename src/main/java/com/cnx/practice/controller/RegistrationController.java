package com.cnx.practice.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cnx.practice.model.User;
import com.cnx.practice.service.IUserService;

@Controller
public class RegistrationController {

	private static final Logger logger = LogManager.getLogger(RegistrationController.class);

	@Autowired
	IUserService userService;

	@PostMapping("/addUser")
	public String registerUser(@ModelAttribute("user") User user, BindingResult result, Model model) {
		// Validate form fields, handle errors

		if (result.hasErrors()) {
			return "sign-up";
		}

		User existingUser = userService.findByUserEmail(user.getUserEmail());
		if (existingUser != null) {
			model.addAttribute("error", "User with this email already exists");
			return "sign-up";
		}

		// Save user to database
		userService.save(user);

		logger.info("User registration request received");

		return "redirect:/login"; // Redirect to login page after successful registration
	}

}
