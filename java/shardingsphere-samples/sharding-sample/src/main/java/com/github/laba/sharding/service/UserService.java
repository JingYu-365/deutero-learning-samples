package com.github.laba.sharding.service;

import java.sql.SQLException;
import java.util.List;

import com.github.laba.sharding.entity.User;

public interface UserService {
	
	void processUsers() throws SQLException;
	
	List<User> getUsers() throws SQLException;

}
