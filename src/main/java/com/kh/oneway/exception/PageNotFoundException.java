package com.kh.oneway.exception;

public class PageNotFoundException extends RuntimeException {
	
	public PageNotFoundException(String message) {
		super(message);
	}
}