package com.github.laba.sharding.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.laba.sharding.entity.User;
import com.github.laba.sharding.repository.UserRepository;
import com.github.laba.sharding.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void processUsers() throws SQLException {
		insertUsers();
	}

	private List<Long> insertUsers() throws SQLException {
		List<Long> result = new ArrayList<>(10);
		for (long i = 1L; i <= 10; i++) {
			User user = new User();
			user.setUserId(i);
			user.setUserName("user_" + i);
			userRepository.addEntity(user);
			result.add(user.getUserId());
			System.out.println("Insert User:" + user.getUserId());
	        
		}
		return result;
	}

	@Override
	public List<User> getUsers() throws SQLException {
		return userRepository.findEntities();
	}
}
