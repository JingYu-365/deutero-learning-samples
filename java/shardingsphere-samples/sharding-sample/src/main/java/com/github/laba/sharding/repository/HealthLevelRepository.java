package com.github.laba.sharding.repository;

import org.apache.ibatis.annotations.Mapper;

import com.github.laba.sharding.entity.HealthLevel;

@Mapper
public interface HealthLevelRepository extends BaseRepository<HealthLevel, Long> {

}
