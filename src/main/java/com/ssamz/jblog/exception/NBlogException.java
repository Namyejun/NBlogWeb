package com.ssamz.jblog.exception;

public class NBlogException extends RuntimeException{
	private static final long serialVersionUID = 1L; // 역직렬화 오류를 회피하기 위한 방법

	public NBlogException(String message) {
		super(message);
	}
}