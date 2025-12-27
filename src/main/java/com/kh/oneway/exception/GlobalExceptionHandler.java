package com.kh.oneway.exception;

import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * [공통 응답 생성 메서드]
	 * - RuntimeException의 메시지를 꺼내 JSON 형태로 감싸서 ResponseEntity로 반환
	 * @param e - 발생한 런타임 예외
	 * @param status - HTTP 상태 코드 
	 * @return - { "message": 예외메시지 } 형태의 응답
	 */
	private ResponseEntity<Map<String, String>> createResponseEntity(RuntimeException e, HttpStatus status){
		Map<String, String> error = new HashMap();
		error.put("message", e.getMessage());
		return ResponseEntity.status(status).body(error);
	}
	
	/**
	 * [인증 실패 처리]
	 * - 로그인 실패, 토큰 오류 등 "인증(Authentication) 문제"를 의미
	 * - Spring Security 흐름에서 컨트롤러까지 도달한 인증 예외 처리
	 * @param e
	 * @return
	 */
	@ExceptionHandler(CustomAuthenticationException.class)
	public ResponseEntity<Map<String, String>> handle(CustomAuthenticationException e){
		return createResponseEntity(e, HttpStatus.UNAUTHORIZED);
	}
	
	/**
	 * [사용자 식별 실패]
	 * - 존재하지 않는 사용자 조회
	 * - 로그인 시 아이디가 존재하지 않을 경우 등
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UserNameNotFoundException.class)
	public ResponseEntity<?> handlerUserNameNotFound(UserNameNotFoundException e){
		Map<String, String> error = new HashMap();
		error.put("message", e.getMessage());
		return ResponseEntity.badRequest().body(error);
	}
	
	/**
	 * [DTO 검증 실패]
	 * - Valid / Validated 사용 시 DTO 유효성 검사 실패
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class) // Controller - dto에서 Valid로 인해 에러응답할때 어떤 에러인지 정보를 담기위해서
	public ResponseEntity<?> handlerArgumentsNotValid(MethodArgumentNotValidException e){
		Map<String, String> errors = new HashMap();
		e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);
	}
	
	/**
	 * [잘못된 파라미터 처리]
	 * - 값 범위 오류, 필수 파라미터 누락 등
	 * @param e
	 * @return
	 */
	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<Map<String, String>> handleInvalidParameter(InvalidParameterException e){
		Map<String, String> error = new HashMap();
		error.put("message", e.getMessage());
		return ResponseEntity.badRequest().body(error);
	}
	
	/**
	 * [페이지 또는 리소스 미존재]
	 * - 조회하려는 데이터, 페이지가 없을 때 등
	 * @param e
	 * @return
	 */
	@ExceptionHandler(PageNotFoundException.class)
	public ResponseEntity<Map<String, String>> handlePageNotFound(PageNotFoundException e){
		Map<String, String> error = new HashMap();
		error.put("message", e.getMessage());
		return ResponseEntity.badRequest().body(error);
	}

	/**
	 * [SQL 처리결과 실패]
	 * - INSERT / UPDATE / DELETE 처리 결과가 기대와 다를 때
	 * @param e
	 * @return
	 */
	@ExceptionHandler(SQLResponseException.class)
	public ResponseEntity<Map<String, String>> handleSQLResponse(SQLResponseException e){
		Map<String, String> error = new HashMap();
		error.put("message", e.getMessage());
		return ResponseEntity.badRequest().body(error);
	}
	
	/**
	 * [JDBC SQL 예외처리]
	 * 내부구현 / 쿼리정보 노출 방지
	 * @param e
	 * @return
	 */
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<Map<String, String>> handleSQLResponse(SQLException e){
		Map<String, String> error = new HashMap();
		error.put("message", "서버에 문제가 발생했습니다.");
		return ResponseEntity.badRequest().body(error);
	}
	
	/**
	 * [Spring DataAccess 예외 처리]
	 * - JDBC, MyBatis, JPA 등에서 공통으로 발생하는 DB 접근 예외
	 * - SQLException의 상위 추상화 개념
	 * 내부 원인은 로그로만 남기고,
	 * 사용자에게는 공통 서버 오류 메시지 전달
	 * @param e
	 * @return
	 */
	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<Map<String, String>> handleSQLResponse(DataAccessException e){
		Map<String, String> error = new HashMap();
		error.put("message", "서버에 문제가 발생했습니다.");
		return ResponseEntity.badRequest().body(error);
	}

	
}
