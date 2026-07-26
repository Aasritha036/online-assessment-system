package com.aasritha.onlineassessment.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

	private static final Logger logger = LogManager.getLogger(UserController.class);

//	Logger logger = Logger.getLogger(UserController.class.getName());

	@GetMapping("/")
	public String viewIndexPage() {
		logger.info("Index Page accessed");
		return "index";
	}

	@GetMapping("/login")
	public String viewLoginPage() {
		logger.info("Login Page accessed");
		return "sign-in";
	}

	@GetMapping("/register")
	public String viewRegisterPage() {
		logger.info("Register Page accessed");
		return "sign-up";
	}

}
