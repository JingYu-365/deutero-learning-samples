package me.jkong.object.storage.core.userauth.mapper;

import java.util.Date;
import java.util.List;

import me.jkong.object.storage.core.userauth.entity.TokenInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;


@Mapper
public interface TokenInfoMapper {

    public void addToken(@Param("token") TokenInfo tokenInfo);

    public void updateToken(@Param("token") String token, @Param("expireTime") int expireTime,
                            @Param("isActive") int isActive);

    public void refreshToken(@Param("token") String token, @Param("refreshTime") Date refreshTime);

    public void deleteToken(@Param("token") String token);

    @ResultMap("TokenInfoResultMap")
    public TokenInfo getTokenInfo(@Param("token") String token);


    @ResultMap("TokenInfoResultMap")
    public List<TokenInfo> getTokenInfoList(@Param("creator") String creator);
}
