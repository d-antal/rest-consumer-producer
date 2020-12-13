package com.rindus.task.restconsumer.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rindus.task.restconsumer.model.DataProducerConstants;
import com.rindus.task.restconsumer.model.Post;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private RestTemplate restTemplate;

	private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);
	private final static String BASE_URI_POST = "https://jsonplaceholder.typicode.com/posts/";

	public List<Post> getPosts() {
		return Arrays.asList(restTemplate.exchange(BASE_URI_POST, HttpMethod.GET, new HttpEntity<String>(createHeader()), Post[].class).getBody());

	}

	public Post getPostById(Integer id) {
		return restTemplate.exchange(BASE_URI_POST + id, HttpMethod.GET, new HttpEntity<String>(createHeader()), Post.class).getBody();

	}

	public Post createPost(Post post) {
		return restTemplate.exchange(BASE_URI_POST, HttpMethod.POST, new HttpEntity<Post>(post, createHeader()), Post.class).getBody();

	}

	public Post updatePost(Integer id, Post post) {
		return restTemplate.exchange(BASE_URI_POST + id, HttpMethod.PUT, new HttpEntity<Post>(post, createHeader()), Post.class).getBody();

	}

	public void deletePost(Integer id) {
		restTemplate.exchange(BASE_URI_POST + id, HttpMethod.DELETE, new HttpEntity<String>(createHeader()), Post.class).getBody();
		LOGGER.info(DataProducerConstants.RESOURCE_DELETED + id);
	}

	private HttpHeaders createHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
