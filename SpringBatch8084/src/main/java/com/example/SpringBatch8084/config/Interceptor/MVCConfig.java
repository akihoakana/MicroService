package com.example.SpringBatch8084.config.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {
    @Autowired
    private HeaderInterceptorConfig headerInterceptorConfig;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Custom interceptor, add intercept path and exclude intercept path
        registry.addInterceptor(headerInterceptorConfig).addPathPatterns("/**");
//        registry.addInterceptor(new InterceptorWithAddPathConfig()).addPathPatterns("/job/yml");
//        registry.addInterceptor(new InterceptorConfig());
    }
}
