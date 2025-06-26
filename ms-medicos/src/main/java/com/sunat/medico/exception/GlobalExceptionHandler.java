package com.sunat.medico.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleConstraintViolation(DataIntegrityViolationException ex) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("status", 422);
		response.put("message", "No se puede registrar el mismo medico");
		return ResponseEntity.unprocessableEntity().body(response);
	}
}