package com.example.restservice.aspect;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogParamAspect {

  private Logger logger = Logger.getLogger(LogParamAspect.class.getName());

  @Pointcut("within(com.example.restservice.controller.GreetingController)")
  public void stringProcessingMethods() {
  }

  @After("stringProcessingMethods()")
  public void logMethodCall(JoinPoint jp) {
    Arrays.stream(jp.getArgs())
        .forEach(o -> logger.log(Level.INFO, "входящие параметры REST API: " + o.toString()));
  }

  @AfterReturning(pointcut = "within(com.example.restservice.controller.GreetingController)", returning = "result")
  public void logAfterReturning(Object result) {
    logger.log(Level.INFO, "исходящие параметры REST API: " + result.toString());
  }
}
