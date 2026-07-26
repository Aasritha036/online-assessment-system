package com.aasritha.onlineassessment.service;

import com.aasritha.onlineassessment.model.User;

public interface IUserService {

	public User findByUserEmail(String userEmail);

	public void save(User user);

	public boolean login(String string, String string2);

}
