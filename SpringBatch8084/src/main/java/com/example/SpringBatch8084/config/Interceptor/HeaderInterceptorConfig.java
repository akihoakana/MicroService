package com.example.SpringBatch8084.config.Interceptor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpHeaders;
import java.util.Date;

@Component
public class HeaderInterceptorConfig implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            SpecifyHeaderInterceptor needLogin = ((HandlerMethod) handler).getMethodAnnotation(SpecifyHeaderInterceptor.class);
            if (null == needLogin) {
                needLogin = ((HandlerMethod) handler).getMethod().getDeclaringClass()
                        .getAnnotation(SpecifyHeaderInterceptor.class);
            }
            // Check login if you have login validation annotations
            if (null != needLogin) {
                System.out.println("Hello i'm preHandle HeaderInterceptorConfig");
                String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJyb2xlc1wiOlwiW1JPTEVfQ1VTVE9NRVJfREVGQVVMVCwgUk9MRV9BRE1JTl1cIixcInR5cGVcIjpcImF1dGhlblwiLFwiZW1haWxcIjpcImFraXp6enp6YTc1Njc1MTMxM0BnbWFpbC5jb21cIn0iLCJpYXQiOjE2ODIzOTE2NzMsImV4cCI6MTY4MjgyMzY3M30.iu02tQMAHgyUmxfPkJHPfMIKf39jK8Ofo5d0VrkFwLA";
                response.setHeader("Authorization", token);
                response.setHeader("Content-Type", "application/json");
                response.setHeader("Connection", "keep-alive");
                if (StringUtils.hasText(response.getHeader("Authorization")))
                    System.out.println("httpHeaders response = " + response.getHeader("Authorization"));
                else System.out.println("fail");

            }
        }
        return true;
    }

    /**
     * Executed before after handler is executed
     **/
    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final ModelAndView modelAndView) throws Exception {
        System.out.println("httpHeaders postHandle= " + request.getHeader("Authorization"));
        System.out.println("httpHeaders response postHandle= " + response.getHeader("Authorization"));


    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler, final Exception ex) throws Exception {
        System.out.println("httpHeaders afterCompletion= " + request.getHeader("Authorization"));
        System.out.println("httpHeaders response afterCompletion= " + response.getHeader("Authorization"));

    }
}
