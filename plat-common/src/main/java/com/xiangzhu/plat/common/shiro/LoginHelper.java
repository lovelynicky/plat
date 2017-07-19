package com.xiangzhu.plat.common.shiro;

import com.xiangzhu.plat.domain.exception.LoginException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ShenHuaJie
 * @version 2016年5月20日 下午3:44:45
 */
public final class LoginHelper {
	private LoginHelper() {
	}

	/** 用户登录 */
	public static final Boolean login(String account, String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(account, password);
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return subject.isAuthenticated();
		} catch (LockedAccountException e) {
			throw new LoginException("账户被锁住");
		} catch (DisabledAccountException e) {
			throw new LoginException("禁用");
		} catch (ExpiredCredentialsException e) {
			throw new LoginException("失效");
		} catch (Exception e) {
			throw new LoginException("登录异常");
		}
	}
}
