package com.rindus.task.restconsumer.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rindus.task.restconsumer.model.DataProducerConstants;

@ControllerAdvice
public class ExceptionHander extends ResponseEntityExceptionHandler {

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<?> resourceNotFoundException(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(new Date(), ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(HttpServerErrorException.class)
	public ResponseEntity<?> restApiServerError(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(new Date(), DataProducerConstants.REMOTE_API_SERVER_ERROR + ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> internalServerError(Exception ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(new Date(), ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
