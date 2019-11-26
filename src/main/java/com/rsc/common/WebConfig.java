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
            "/rsc/postman/plogin",
            "/rsc/postman/ptologin",
            "/rsc/customer/cindex",
            "/rsc/customer/register",
            "/rsc/customer/clogin",
            "/rsc/customer/ctologin",
            "/rsc/customer/toregister",
            "/rsc/postman/assignfault",
            "/rsc/postman/pdetermineassigned"

    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns(addPathPatterns).excludePathPatterns(excludePathPatterns);
    }
}
