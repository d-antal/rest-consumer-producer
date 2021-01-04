package com.rindus.task.restconsumer.model;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ApiRequestEntityBuilder {

	public HttpEntity<String> noBodyRequestEntity(MediaType accept, MediaType contentType) {
		return new HttpEntity<String>(buildHeader(accept, contentType));
	}

	public HttpEntity<Post> postRequestEntity(Post post, MediaType accept, MediaType contentType) {
		return new HttpEntity<Post>(post, buildHeader(accept, contentType));
	}

	public HttpEntity<Comment> commentRequestEntity(Comment comment, MediaType accept, MediaType contentType) {
		return new HttpEntity<Comment>(comment, buildHeader(accept, contentType));
	}

	private HttpHeaders buildHeader(MediaType accept, MediaType contentType) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { accept }));
		headers.setContentType(contentType);
		return headers;
	}
}
