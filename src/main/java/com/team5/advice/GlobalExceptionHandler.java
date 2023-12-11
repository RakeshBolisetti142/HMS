package com.team5.advice;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.team5.dto.ApiError;
import com.team5.exceptions.InvalidDepartmentException;
import com.team5.exceptions.InvalidTypeConversionException;
import com.team5.exceptions.InvalidValueException;
import com.team5.exceptions.NotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(InvalidTypeConversionException.class)
	public ResponseEntity<ApiError> handleInvalidTypeConversion(InvalidTypeConversionException ex){
		ApiError error=new ApiError();
		error.setMsg(ex.getMessage());
		error.setStatus(400);
		error.setTimeStamp(LocalDateTime.now());
		
		return new ResponseEntity<ApiError>(error,
				HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(NoResourceFoundException.class)
	public ResponseEntity<ApiError> handleStaticResourceNotFound(NoResourceFoundException ex){
		ApiError error=new ApiError();
		error.setMsg("URL you are looking for is not found");
		error.setStatus(404);
		error.setTimeStamp(LocalDateTime.now());
		
		return new ResponseEntity<ApiError>(error,
				HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiError> handleInvalidTypeConversionBySystemException(MethodArgumentTypeMismatchException ex){
		ApiError error=new ApiError();
		error.setMsg("Id must be Integer");
		error.setStatus(400);
		error.setTimeStamp(LocalDateTime.now());
		//return error;
		return new ResponseEntity<ApiError>(error,
				HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(InvalidValueException.class)
	public ResponseEntity<ApiError> handleInvalidValueException(InvalidValueException ex){
		ApiError error=new ApiError();
		error.setMsg(ex.getMessage());
		error.setStatus(500);
		error.setTimeStamp(LocalDateTime.now());
		return new ResponseEntity<ApiError>(error,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handleTrainedInException(NotFoundException ex){
		ApiError error=new ApiError();
		error.setMsg(ex.getMessage());
		error.setStatus(404);
		error.setTimeStamp(LocalDateTime.now());
		return new ResponseEntity<ApiError>(error,
				HttpStatus.valueOf(404));
	}
	
	@ExceptionHandler(InvalidDepartmentException.class)
	public ResponseEntity<ApiError> handleTrainedInException(InvalidDepartmentException ex){
		ApiError error=new ApiError();
		error.setMsg(ex.getMessage());
		error.setStatus(404);
		error.setTimeStamp(LocalDateTime.now());

		return new ResponseEntity<ApiError>(error,
				HttpStatus.valueOf(404));
	}
	
	@ExceptionHandler(Exception.class)
	public  ResponseEntity<ApiError> handle(Exception e) {
		ApiError error=new ApiError();
		error.setMsg(e.getMessage());
		error.setStatus(404);
		error.setTimeStamp(LocalDateTime.now());

		return new ResponseEntity<ApiError>(error,
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
	
}
