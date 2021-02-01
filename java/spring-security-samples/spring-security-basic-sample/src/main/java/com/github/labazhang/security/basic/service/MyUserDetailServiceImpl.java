package com.github.labazhang.security.basic.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.labazhang.security.basic.entity.Users;
import com.github.labazhang.security.basic.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自定义实现 {@link UserDetailsService}
 *
 * @author laba zhang
 */
@Service("userDetailService")
public class MyUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UsersMapper usersMapper;

    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<Users> queryWrapper = new QueryWrapper<Users>();
        queryWrapper.eq("username", s);
        Users user = usersMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在.");
        }
        List<GrantedAuthority> authorityList =
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_sale");
        return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), authorityList);
    }
}
