package me.jkong.object.storage.core.usermgt.service;

import com.google.common.base.Strings;
import me.jkong.object.storage.core.userauth.entity.TokenInfo;
import me.jkong.object.storage.core.userauth.mapper.TokenInfoMapper;
import me.jkong.object.storage.core.usermgt.entity.UserInfo;
import me.jkong.object.storage.core.usermgt.mapper.UserInfoMapper;
import me.jkong.object.storage.core.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {

    //set expireTime is better
    private long LONG_REFRESH_TIME = 4670409600000L;
    private int LONG_EXPIRE_TIME = 36500;

    public final static String SYSTEM_USER = "SuperAdmin";

    final UserInfoMapper userInfoMapper;

    final TokenInfoMapper tokenInfoMapper;

    @Autowired
    public UserServiceImpl(UserInfoMapper userInfoMapper, TokenInfoMapper tokenInfoMapper) {
        this.userInfoMapper = userInfoMapper;
        this.tokenInfoMapper = tokenInfoMapper;
    }

    @Override
    public boolean addUser(UserInfo userInfo) {
        userInfoMapper.addUser(userInfo);
        //todo add token
        Date date = new Date();
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setToken(userInfo.getUserId());
        tokenInfo.setActive(true);
        tokenInfo.setCreateTime(date);
        tokenInfo.setCreator(SYSTEM_USER);
        tokenInfo.setExpireTime(LONG_EXPIRE_TIME);
        tokenInfo.setRefreshTime(date);
        tokenInfoMapper.addToken(tokenInfo);
        return true;
    }

    @Override
    public boolean updateUserInfo(String userId, String password, String detail) {
        userInfoMapper
                .updateUserInfo(userId,
                        Strings.isNullOrEmpty(password) ? null : MD5Util.getMd5Password(password),
                        Strings.emptyToNull(detail));
        return true;
    }

    @Override
    public boolean deleteUser(String userId) {
        userInfoMapper.deleteUser(userId);
        //todo delete token
        tokenInfoMapper.deleteToken(userId);
        return true;
    }

    @Override
    public UserInfo getUserInfo(String userId) {
        return userInfoMapper.getUserInfo(userId);
    }

    @Override
    public UserInfo checkPassword(String userName, String password) {
        return userInfoMapper.checkPassword(userName, password);
    }

    @Override
    public UserInfo getUserInfoByName(String userName) {
        return userInfoMapper.getUserInfoByName(userName);
    }
}