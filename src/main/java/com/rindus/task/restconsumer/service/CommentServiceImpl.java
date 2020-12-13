package com.rindus.task.restconsumer.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rindus.task.restconsumer.model.Comment;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private RestTemplate restTemplate;

	private final static String BASE_URI_COMMENT = "https://jsonplaceholder.typicode.com/comments/";

	@Override
	public Comment getCommentById(Integer id) {
		return restTemplate.exchange(BASE_URI_COMMENT + id, HttpMethod.GET, new HttpEntity<String>(createHeader()), Comment.class).getBody();
	}

	private HttpHeaders createHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
