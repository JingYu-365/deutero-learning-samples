package com.github.laba.sharding.service;

import java.sql.SQLException;

public interface HealthRecordService {
	
	public void processHealthRecords() throws SQLException;

}
