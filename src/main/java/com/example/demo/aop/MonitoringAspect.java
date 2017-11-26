package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.example.demo.aop.annotation.Loggable;

@Component
@Aspect
public class MonitoringAspect {

	@Pointcut("@annotation(com.example.demo.aop.annotation.Measurable)")
	public void measurableMethod() {
	}

	@Pointcut(value = "@annotation(com.example.demo.aop.annotation.Loggable)")
	public void loggableMethod() {
	}

	@Before("loggableMethod() && @annotation(annot)")
	public void logBeforeMethod(JoinPoint jp, Loggable annot) {
		System.out.println(String.format("Logging " + annot.value() + " In"));
	}

	@AfterReturning("loggableMethod() && @annotation(annot)")
	public void logAfterMethod(JoinPoint jp, Loggable annot) {
		System.out.println(String.format("Logging " + annot.value() + " Out"));
	}

	@AfterThrowing(pointcut = "loggableMethod() && @annotation(annot)", throwing = "ex")
	public void logFailedMethod(JoinPoint jp, Loggable annot, Exception ex) {
		System.out.println(String.format("Logging " + annot.value() + " Exception: " + ex));
	}

	@Around("@annotation(com.example.demo.aop.annotation.Measurable)")
	public Object measureExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.nanoTime();
		Object ret = pjp.proceed();
		long stop = System.nanoTime();
		System.out.println("Execution time = " + (stop - start) + " ms");
		return ret;
	}
}
