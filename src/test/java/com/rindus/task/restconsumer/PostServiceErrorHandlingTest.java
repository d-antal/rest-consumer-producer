package com.rindus.task.restconsumer;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.rindus.task.restconsumer.exception.ApiResponseErrorHandler;
import com.rindus.task.restconsumer.exception.ResourceNotFoundException;
import com.rindus.task.restconsumer.model.Post;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { Post.class })
@RestClientTest
public class PostServiceErrorHandlingTest {

	@Autowired
	private MockRestServiceServer server;

	@Autowired
	private RestTemplateBuilder builder;

	private RestTemplate restTemplate;

	private final static Integer ID_NOT_EXISTS = 1234567;
	private final static String REMOTE_API_POST_URI = "https://jsonplaceholder.typicode.com/posts/";
	private final static HttpEntity<String> STRING_ENTITY = new HttpEntity<String>(createHeader());

	@Before
	public void init() {
		restTemplate = this.builder.errorHandler(new ApiResponseErrorHandler()).build();
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetPostByIdErrorHandlingWhenPostNotExists() {
		this.server.expect(ExpectedCount.once(), requestTo(REMOTE_API_POST_URI + ID_NOT_EXISTS)).andExpect(method(HttpMethod.GET)).andRespond(withStatus(HttpStatus.NOT_FOUND));

		restTemplate.exchange(REMOTE_API_POST_URI + ID_NOT_EXISTS, HttpMethod.GET, STRING_ENTITY, Post.class).getBody();
		this.server.verify();
	}

	private static HttpHeaders createHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}
}
