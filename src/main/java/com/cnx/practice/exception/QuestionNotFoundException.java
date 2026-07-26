package com.cnx.practice.exception;

public class QuestionNotFoundException extends RuntimeException {
	
	public QuestionNotFoundException(String msg) {
		super(msg);
	}
}
