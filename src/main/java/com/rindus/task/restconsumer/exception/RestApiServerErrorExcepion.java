package com.rindus.task.restconsumer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class RestApiServerErrorExcepion extends HttpServerErrorException {
	
	private static final long serialVersionUID = 1L;

	public RestApiServerErrorExcepion(HttpStatus message) {
		super(message);
	}

}
