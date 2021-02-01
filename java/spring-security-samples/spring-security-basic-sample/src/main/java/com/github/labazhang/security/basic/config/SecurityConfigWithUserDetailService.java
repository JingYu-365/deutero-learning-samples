package com.github.labazhang.security.basic.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * 通过实现 UserDetailService 方式，定义用户来源
 *
 * @author laba zhang
 */
@Configuration
public class SecurityConfigWithUserDetailService extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    private final DataSource dataSource;

    public SecurityConfigWithUserDetailService(@Qualifier("userDetailService") UserDetailsService userDetailsService,
                                               DataSource dataSource) {
        this.userDetailsService = userDetailsService;
        this.dataSource = dataSource;
    }

    /**
     * 记住我 Token 持久化Bean配置
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        // 赋值数据源
        jdbcTokenRepository.setDataSource(dataSource);
        // 自动创建表,第一次执行会创建，以后要执行就要删除掉！
        // jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 没有权限访问 403 页面
        http.exceptionHandling().accessDeniedPage("/noauth.html");

        // 配置认证
        http.formLogin()
                // 配置哪个 url 为登录页面
                .loginPage("/login.html")
                // 设置哪个是登录的 url
                .loginProcessingUrl("/user/login")
                // 登录成功之后跳转到哪个 url
                .defaultSuccessUrl("/index").permitAll();

        // 配置记住我
        http.rememberMe()
                //token 过期时间
                .tokenValiditySeconds(60)
                // token 持久化配置
                .tokenRepository(persistentTokenRepository())
                // 用户操作实现
                .userDetailsService(userDetailsService);

        // 配置登出
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/index").permitAll();

        // 配置路径访问权限
        http.authorizeRequests()
                // 表示配置请求路径,指定 URL 无需保护。
                .antMatchers("/", "/user/login", "/index").permitAll()
                // 配置需要权限的路径
                .antMatchers("/hello/auth").hasAuthority("admin")
                .antMatchers("/hello/noauth").hasAuthority("no")
                // 配置指定角色才可以访问
                .antMatchers("/hello/role").hasRole("sale")
                .antMatchers("/hello/norole").hasRole("no")
                // 其他请求需要认证
                .anyRequest().authenticated();

        // 关闭 csrf
        http.csrf().disable();
    }

    /**
     * 配置认证信息
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

