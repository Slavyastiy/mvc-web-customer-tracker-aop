package com.luv2code.springdemo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {
	
	// setup logger
	private Logger myLogger = Logger.getLogger(getClass().getName());

	// setup pointcut declarations
	@Pointcut("execution(* com.luv2code.springdemo.controller.*.*(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.luv2code.springdemo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	// add @before advice
	@Before("forAppFlow()")
	public void before(JoinPoint theJoinPoints) {
		
		//display method we are calling
		String theMethod = theJoinPoints.getSignature().toShortString();
		myLogger.info("=====>> in @Before: calling method" + theMethod);
	
		// display the arguments to the method
		
		Object[] args = theJoinPoints.getArgs();
		
		for(Object tempArgs: args) {
			myLogger.info("====>> argument: " + tempArgs);
		}

	}
	
	
	
	
	// add @AfterReturning advice
	
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="theResult"
			)
	public void afterReturning(JoinPoint theJoinPoint, Object theResult) {
		
		// display method we are returning 
		String theMethod = theJoinPoint.getSignature().toShortString();
		myLogger.info("=====>> in @AfterReturning: calling method" + theMethod);
		
		//display data returned
		myLogger.info("====>> result: " + theResult);
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
