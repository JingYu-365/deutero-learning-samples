package com.github.laba.sharding.repository;

import com.github.laba.sharding.entity.EncryptUser;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EncryptUserRepository extends BaseRepository<EncryptUser, Long> {

}
