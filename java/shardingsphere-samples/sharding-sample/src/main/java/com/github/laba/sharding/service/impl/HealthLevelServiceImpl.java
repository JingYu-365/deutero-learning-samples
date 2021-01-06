package com.github.laba.sharding.service.impl;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.laba.sharding.entity.HealthLevel;
import com.github.laba.sharding.repository.HealthLevelRepository;
import com.github.laba.sharding.service.HealthLevelService;

@Service
public class HealthLevelServiceImpl implements HealthLevelService {

	@Autowired
	private HealthLevelRepository healthLevelRepository;
	
	@Override
	public void processLevels() throws SQLException {
		healthLevelRepository.addEntity(new HealthLevel(1L, "level1"));
		healthLevelRepository.addEntity(new HealthLevel(2L, "level2"));
		healthLevelRepository.addEntity(new HealthLevel(3L, "level3"));
		healthLevelRepository.addEntity(new HealthLevel(4L, "level4"));
		healthLevelRepository.addEntity(new HealthLevel(5L, "level5"));
	}
}
