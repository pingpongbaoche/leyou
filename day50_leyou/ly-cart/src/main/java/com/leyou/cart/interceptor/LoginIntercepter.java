package com.leyou.cart.interceptor;
import com.leyou.auth.entiy.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.cart.config.JwtProperties;
import com.leyou.common.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器 用springmvc的拦截器的前置方法
 * */
@Component//加入spring容器中
@EnableConfigurationProperties(JwtProperties.class)
public class LoginIntercepter extends HandlerInterceptorAdapter {//继承HandlerInterceptorAdapter而不是接口的话可以不用实现三个方法

    @Autowired
    private JwtProperties jwtProperties;

//    public static UserInfo userInfo; 不行，存在线程安全问题

    private static final ThreadLocal<UserInfo> THREAD_LOCAL = new ThreadLocal<>();//局部的线程变量


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取cookie中的token
        String token = CookieUtils.getCookieValue(request, this.jwtProperties.getCookieName());

        //解析token，获取用户信息
        UserInfo userInfo = JwtUtils.getInfoFromToken(token, this.jwtProperties.getPublicKey());
        if(userInfo == null){
            return false;
        }

        //把userInfo放入线程局部变量
        THREAD_LOCAL.set(userInfo);

        return true;
    }

    //因为是私有的提供不了，所以写一个方法调用得到UserInfo
    public static UserInfo getUserInfo(){
        return THREAD_LOCAL.get();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空线程局部变量，必须，因为使用的是tomcat的线程池，线程不会结束，也就不会释放线程的局部变量，需要手动释放
        THREAD_LOCAL.remove();
    }
}
