package com.traceprice.takeoffer.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class QueryAspect {
    private long startTime;

    @Before("execution(* com.traceprice.takeoffer.Repository.VenderRepository.findItemsByPnameAndPriceDate(..)) || " +
            "execution(* com.traceprice.takeoffer.Repository.VenderRepository.findItemsByProductTypeAndPriceDate(..))")
    public void before() {
        startTime = System.currentTimeMillis();
    }

    @After("execution(* com.traceprice.takeoffer.Repository.VenderRepository.findItemsByPnameAndPriceDate(..)) || " +
            "execution(* com.traceprice.takeoffer.Repository.VenderRepository.findItemsByProductTypeAndPriceDate(..))")
    public void after() {
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        System.out.println("쿼리 실행 시간: " + executionTime + " ms");
    }
}
