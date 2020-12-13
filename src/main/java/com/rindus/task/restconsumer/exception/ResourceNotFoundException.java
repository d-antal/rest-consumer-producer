package com.rindus.task.restconsumer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends HttpClientErrorException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(HttpStatus message) {
		super(message);
	}

}
