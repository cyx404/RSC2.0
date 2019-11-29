package com.rsc.common;

import com.rsc.common.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName:Config
 * @Description:TODO 拦截路径配置
 * @Author:chenyx
 * @Date:Create in  2019/11/16 15:44
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    //需要拦截的路径
    private String[] addPathPatterns = {
            "/rsc/**"
    };

    //不需拦截的路径
    private String[] excludePathPatterns = {
            "/rsc/pm/plogin",
            "/rsc/pm/ptologin",
            "/rsc/customer/cindex",
            "/rsc/customer/register",
            "/rsc/customer/clogin",
            "/rsc/customer/ctologin",
            "/rsc/customer/toregister",
            "/rsc/admin/mlogin",
            "/rsc/admin/mtologin",
            "/rsc/admin/echarts-en.common.js",
            "/rsc/admin/echarts.min.js",
            "/rsc/admin/jquery-3.4.1.min.js",
            "/rsc/admin/js/**",
            "/rsc/admin/css/**",
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);
    }
}
