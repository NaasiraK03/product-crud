package org.example.productcrud.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class LoggingAspect {
    // Initialize the SLF4J logger for this class
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

   @Before("execution(* org.example.productcrud.service.ProductService.*(..))")
   public void logBeforeMethod(JoinPoint joinPoint){
     logger.info("Calling Method: {}",joinPoint.getSignature().getName());
   }



}
