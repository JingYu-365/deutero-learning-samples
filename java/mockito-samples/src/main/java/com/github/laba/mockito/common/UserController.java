package com.github.laba.mockito.common;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller
 *
 * @author laba zhang
 */
public class UserController extends HttpServlet {

    private UserDao userDao;

    protected UserInfo queryUserInfoById(HttpServletRequest req) {
        String id = req.getParameter("id");
        return userDao.queryUserInfoById(id);
    }
}
