package com.xiangzhu.plat.common.shiro;

import com.xiangzhu.plat.domain.UserLoginCheck;
import com.xiangzhu.plat.utils.JerseyClient;
import com.xiangzhu.plat.utils.MapUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liluoqi on 2017/7/10.
 */
public class CustomRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(CustomRealm.class);

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return new SimpleAuthorizationInfo();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        try {
            UserLoginCheck userLoginCheck = JerseyClient.buildHttpJerseyClient("localhost:8088", "plat").post("service/user/validate",
                    MapUtils.convertStringObjectMap("username", token.getUsername(),"password",String.valueOf(token.getPassword())), UserLoginCheck.class);
            if (userLoginCheck != null && userLoginCheck.isValid()) {
                this.setSession("currentUsername", token.getUsername());
                return new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), getName());
            }
        } catch (Throwable e) {
            logger.error("请求用户信息接口失败", e);
        }
        return null;
    }

    private Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
        } catch (InvalidSessionException e) {
            logger.error("获取shiro session失败", e);
        }
        return null;
    }

    /**
     * 保存登录名
     */
    private void setSession(Object key, Object value) {
        Session session = getSession();
        System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
        if (null != session) {
            session.setAttribute(key, value);
        }
    }
}
