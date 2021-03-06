package com.rindus.task.restconsumer.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rindus.task.restconsumer.exception.ResourceNotFoundException;
import com.rindus.task.restconsumer.model.DataProducerConstants;
import com.rindus.task.restconsumer.model.ApiRequestEntityBuilder;
import com.rindus.task.restconsumer.model.Post;

@Service
public class PostServiceImpl extends ApiRequestEntityBuilder implements PostService {

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);
	private final static String BASE_URI_POST = "https://jsonplaceholder.typicode.com/posts/";

	public List<Post> getPosts() {
		List<Post> posts = Arrays.asList(restTemplate.exchange(BASE_URI_POST, HttpMethod.GET, noBodyRequestEntity(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON), Post[].class).getBody());
		LOGGER.info(DataProducerConstants.RESOURCE_LIST_AVAILABLE + posts.size());
		return posts;
	}

	public Post getPostById(Integer id) throws ResourceNotFoundException {
		Post postById = restTemplate.exchange(BASE_URI_POST + id, HttpMethod.GET, noBodyRequestEntity(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON), Post.class).getBody();
		LOGGER.info(DataProducerConstants.RESOURCE_AVAILABLE + id);
		return postById;
	}

	public Post createPost(Post post) {
		LOGGER.info(DataProducerConstants.CREATE_RESOURCE);
		return restTemplate.exchange(BASE_URI_POST, HttpMethod.POST, postRequestEntity(post, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON), Post.class).getBody();
	}

	public Post updatePost(Integer id, Post post) throws ResourceNotFoundException {
		this.getPostById(id);
		LOGGER.info(DataProducerConstants.UPDATE_RESOURCE_BY_ID + id);
		return restTemplate.exchange(BASE_URI_POST + id, HttpMethod.PUT, postRequestEntity(post, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON), Post.class).getBody();
	}

	public Integer deletePost(Integer id) throws ResourceNotFoundException {
		this.getPostById(id);
		Integer deletePostId = restTemplate.exchange(BASE_URI_POST + id, HttpMethod.DELETE, noBodyRequestEntity(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON), Post.class).getBody().getId();
		LOGGER.info(DataProducerConstants.RESOURCE_DELETED + id);
		return deletePostId;
	}
}


