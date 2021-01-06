package com.github.laba.sharding.service;

import java.sql.SQLException;

public interface HealthLevelService {

	public void processLevels() throws SQLException;
}
