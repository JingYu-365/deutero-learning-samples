package com.github.laba.sharding.service;

import java.sql.SQLException;
import java.util.List;

import com.github.laba.sharding.entity.EncryptUser;

public interface EncryptUserService {
	
	public void processEncryptUsers() throws SQLException;
	
	public List<EncryptUser> getEncryptUsers() throws SQLException;

}
