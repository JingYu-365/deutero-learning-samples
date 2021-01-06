package com.github.laba.sharding.repository;

import org.apache.ibatis.annotations.Mapper;

import com.github.laba.sharding.entity.HealthTask;

@Mapper
public interface HealthTaskRepository extends BaseRepository<HealthTask, Long> {

}
