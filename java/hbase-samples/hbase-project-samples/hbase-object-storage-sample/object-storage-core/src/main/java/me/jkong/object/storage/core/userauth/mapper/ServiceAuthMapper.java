package me.jkong.object.storage.core.userauth.mapper;


import me.jkong.object.storage.core.userauth.entity.ServiceAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;

@Mapper
public interface ServiceAuthMapper {

    public void addAuth(@Param("auth") ServiceAuth auth);

    public void deleteAuth(@Param("bucket") String bucketName, @Param("token") String token);

    public void deleteAuthByToken(@Param("token") String token);

    public void deleteAuthByBucket(@Param("bucket") String bucketName);

    @ResultMap("ServiceAuthResultMap")
    public ServiceAuth getAuth(@Param("bucket") String bucketName, @Param("token") String token);
}
