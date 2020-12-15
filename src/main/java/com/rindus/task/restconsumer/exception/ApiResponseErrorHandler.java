package com.rindus.task.restconsumer.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import com.rindus.task.restconsumer.model.DataProducerConstants;

@Component
public class ApiResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return (HttpStatus.Series.CLIENT_ERROR == response.getStatusCode().series() || HttpStatus.Series.SERVER_ERROR == response.getStatusCode().series());
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		if (HttpStatus.Series.SERVER_ERROR == response.getStatusCode().series()) {
			throw new HttpClientErrorException(response.getStatusCode());
		} else if (HttpStatus.Series.CLIENT_ERROR == response.getStatusCode().series()) {
			if (HttpStatus.NOT_FOUND == response.getStatusCode()) {
				throw new ResourceNotFoundException(DataProducerConstants.RESOURCE_NOT_FOUND);
			}
		}
	}
}
