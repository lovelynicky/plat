package com.xiangzhu.plat.controller;

import com.xiangzhu.plat.common.shiro.LoginHelper;
import com.xiangzhu.plat.domain.exception.LoginException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liluoqi on 2017/7/10.
 */
@Controller
public class LoginController {

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(HttpServletRequest request) {
        String username = request.getParameter("username");
        if (StringUtils.isBlank(username)) {
            throw new LoginException("用户名不能为空");
        }
        String password = request.getParameter("password");
        if (StringUtils.isBlank(password)) {
            throw new LoginException("密码不能为空");
        }
        if (LoginHelper.login(username, password)) {
            return "redirect:/index";
        }
        logger.error(String.format("用户:%s登录失败", username));
        throw new LoginException("登录失败");
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SecurityUtils.getSubject().logout();
        return "redirect:/login";
    }
}
