package com.github.laba.sharding.hint;

import java.io.IOException;
import java.sql.SQLException;

public interface HintService {

	public void processWithHintValueForShardingDatabases() throws SQLException, IOException;
	
	public void processWithHintValueForShardingDatabasesAndTables() throws SQLException, IOException;
	
	public void processWithHintValueMaster() throws SQLException, IOException;
}
