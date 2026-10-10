package com.example.agriculture.exception;

import java.util.HashMap;

import java.util.Map;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestControllerAdvice
public class GlobalExceptionHandling {
	
	@ExceptionHandler(UserDefineException.class)
	 @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserDefineException(UserDefineException ex) 
	{
		 return ex.getMessage();		
	}
	
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	 public Map<String, String> handleFieldValidations(MethodArgumentNotValidException ex) 
	 {
		 Map<String, String> errors = new HashMap<>();
		 ex.getBindingResult()
		 .getFieldErrors()
		 .forEach(error ->
		 errors.put(error.getField(),error.getDefaultMessage()));
		 
		 return errors;		 
	 }
	 
	 @ExceptionHandler(IllegalArgumentException.class)
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	 public String handleIllegalArgumentException(IllegalArgumentException ex) 
	 {
		 return ex.getMessage();		 
	 }
	 
	 @ExceptionHandler(IllegalStateException.class)
	 @ResponseStatus(HttpStatus.CONFLICT)
	 public String handleIllegalStateException(IllegalStateException ex) 
	 {
		 return ex.getMessage();		 
	 }
	 
	 @ExceptionHandler(DataIntegrityViolationException.class)
	 public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) 
	 {
		 return ResponseEntity .status(HttpStatus.CONFLICT).body("Database constraint violation");
		 
	 }
	 
	 @ExceptionHandler(DataAccessException.class)
	 public ResponseEntity<String> handleDataAccessException(DataAccessException ex) 
	 {
		 return ResponseEntity .status(HttpStatus.INTERNAL_SERVER_ERROR).body("Database operation failed");		 
	 }
	 
	 @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	 public ResponseEntity<String> handleOptimisticLocking(ObjectOptimisticLockingFailureException ex) 
	 {
		 return ResponseEntity .status(HttpStatus.CONFLICT).body("Record was already updated by another user");		 				 
	 }
	 
	 @ExceptionHandler(Exception.class)
	 @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	 public String handleGeneralException(Exception ex) 
	 {
		 return "An unexpected error occurred";
		 
	 }

	 
	 
	 
	 
	 
	 


}
