package com.rindus.task.restconsumer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ConcurentCallException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ConcurentCallException(String message) {
		super(message);
	}

}
