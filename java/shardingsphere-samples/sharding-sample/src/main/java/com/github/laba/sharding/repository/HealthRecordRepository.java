package com.github.laba.sharding.repository;

import org.apache.ibatis.annotations.Mapper;

import com.github.laba.sharding.entity.HealthRecord;

@Mapper
public interface HealthRecordRepository extends BaseRepository<HealthRecord, Long> {

}
