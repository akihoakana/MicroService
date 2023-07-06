package com.example.SpringBatch8084.config.AOP;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AOPConfig {
    private Logger logger = LoggerFactory.getLogger(AOPConfig.class);

    @Before("execution(* com.example.SpringBatch8084.service.*.*(..))")
    public void before(JoinPoint joinPoint) {
        logger.info("AOPConfig before called Service" + joinPoint.toString());
    }

    @After("execution(* com.example.SpringBatch8084.service.*.*(..))")
    public void after(JoinPoint joinPoint) {
        logger.info(" after called " + joinPoint.toString());
    }

    @AfterReturning("execution(* com.example.SpringBatch8084.service.*.*(..))")
    public void afterReturning(JoinPoint joinPoint) {
        logger.info(" afterReturning called " + joinPoint.toString());
    }

    @AfterThrowing("execution(* com.example.SpringBatch8084.service.*.*(..))")
    public void afterThrowing(JoinPoint joinPoint) {
        logger.info(" afterThrowing called " + joinPoint.toString());
    }

    @Around("execution(* com.example.SpringBatch8084.service.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        Long startTime = System.currentTimeMillis();
        logger.info("Start Time All Taken by {} is {}", joinPoint, startTime);

        Long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken All by {} is {}", joinPoint, timeTaken);
        return joinPoint.proceed();

    }

    @Around("@annotation(com.example.SpringBatch8084.config.AOP.TrackTimeAOP)")
    public Object aroundTrackTime(ProceedingJoinPoint joinPoint) throws Throwable {

        Long startTime = System.currentTimeMillis();
        logger.info("Start Time Taken TrackTimeAOP by {} is {}", joinPoint, startTime);

        Long timeTaken = System.currentTimeMillis() - startTime;
        logger.info("Time Taken TrackTimeAOP by {} is {}", joinPoint, timeTaken);
        return joinPoint.proceed();

    }
}
