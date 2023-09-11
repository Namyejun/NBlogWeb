package com.ssamz.jblog.controller.advice;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.ssamz.jblog.dto.ResponseDTO;

@Component
@Aspect
public class ValidationCheckAdvice {
	@Around(value = "execution(* com.ssamz.jblog.controller.*Controller.*(..))")
	public Object validationCheck(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		
		for (Object arg : args) {
			// 인자 목록에 BindingResult 객체가 있다면
			if (arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;
				if (bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<>();
					for (FieldError error : bindingResult.getFieldErrors()) {
						errorMap.put(error.getField(), error.getDefaultMessage());
					}
					return new ResponseDTO<>(HttpStatus.BAD_REQUEST.value(), errorMap);
				}
			}
		}
		return joinPoint.proceed();
	}
}
