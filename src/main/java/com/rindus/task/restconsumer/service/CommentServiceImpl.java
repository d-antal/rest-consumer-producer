package com.rindus.task.restconsumer.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rindus.task.restconsumer.model.Comment;
import com.rindus.task.restconsumer.model.DataProducerConstants;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private RestTemplate restTemplate;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);
	private final static String BASE_URI_COMMENT = "https://jsonplaceholder.typicode.com/comments/";
	
	@Override
	public Comment getCommentById(Integer id) {
		Comment commentById = restTemplate.exchange(BASE_URI_COMMENT + id, HttpMethod.GET, new HttpEntity<String>(createHeader()), Comment.class).getBody();
		LOGGER.info(DataProducerConstants.RESOURCE_AVAILABLE + id);
		return commentById;
	}

	private HttpHeaders createHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
