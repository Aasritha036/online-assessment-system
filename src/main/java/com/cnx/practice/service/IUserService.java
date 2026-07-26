package com.cnx.practice.service;

import com.cnx.practice.model.User;

public interface IUserService {

	public User findByUserEmail(String userEmail);

	public void save(User user);

	public boolean login(String string, String string2);

}
