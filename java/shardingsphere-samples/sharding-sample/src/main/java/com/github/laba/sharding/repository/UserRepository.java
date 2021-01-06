package com.github.laba.sharding.repository;

import org.apache.ibatis.annotations.Mapper;

import com.github.laba.sharding.entity.User;

@Mapper
public interface UserRepository extends BaseRepository<User, Long> {

}
