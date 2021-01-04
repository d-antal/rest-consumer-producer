package com.rindus.task.restconsumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rindus.task.restconsumer.model.Comment;
import com.rindus.task.restconsumer.model.DataProducerConstants;
import com.rindus.task.restconsumer.model.ApiRequestEntityBuilder;

@Service
public class CommentServiceImpl extends ApiRequestEntityBuilder implements CommentService {

	@Autowired
	private RestTemplate restTemplate;

	private final static Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);
	private final static String BASE_URI_COMMENT = "https://jsonplaceholder.typicode.com/comments/";

	@Override
	public Comment getCommentById(Integer id) {
		Comment commentById = restTemplate.exchange(BASE_URI_COMMENT + id, HttpMethod.GET, noBodyRequestEntity(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON), Comment.class).getBody();
		LOGGER.info(DataProducerConstants.RESOURCE_AVAILABLE + id);
		return commentById;
	}
}


