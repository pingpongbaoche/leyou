package com.leyou.cart.config;

import com.leyou.cart.interceptor.LoginIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LeyouWebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginIntercepter loginIntercepter;
    /**
     * 添加自己的拦截器
     * */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginIntercepter).addPathPatterns("/**");//拦截所有路径 /*不行，只拦截一级路径
    }
}
