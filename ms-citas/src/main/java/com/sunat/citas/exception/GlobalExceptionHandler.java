package com.sunat.citas.exception;

import feign.FeignException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FeignException.NotFound.class)
	public ResponseEntity<Object> handleFeignNotFound(FeignException.NotFound ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("status", 422);
		response.put("message", "El médico no existe");
		return ResponseEntity.unprocessableEntity().body(response);
	}

	@ExceptionHandler(FeignException.class)
	public ResponseEntity<Object> handleGenericFeignError(FeignException ex) {
		Map<String, Object> response = new HashMap<>();
		response.put("status", 422);
		response.put("message", ex);
		return ResponseEntity.unprocessableEntity().body(response);
	}
	
	@ExceptionHandler(RecursoNoProcesableException.class)
	public ResponseEntity<Object> handleUnprocessableEntity(RecursoNoProcesableException ex) {
	    Map<String, Object> response = new HashMap<>();
	    response.put("status", 422);
	    response.put("message", ex.getMessage());
	    return ResponseEntity.unprocessableEntity().body(response);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(DataIntegrityViolationException ex) {
        Map<String, Object> response = new HashMap<>();
	    response.put("status", 422);
	    response.put("message", "No se puede registrar el mismo usuario");
	    return ResponseEntity.unprocessableEntity().body(response);
    }
}