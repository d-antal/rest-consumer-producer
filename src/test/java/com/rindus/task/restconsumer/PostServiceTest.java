package com.rindus.task.restconsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.rindus.task.restconsumer.exception.ResourceNotFoundException;
import com.rindus.task.restconsumer.model.ApiRequestEntityBuilder;
import com.rindus.task.restconsumer.model.Post;
import com.rindus.task.restconsumer.service.PostServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class PostServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private PostServiceImpl postService;

	private final static String REMOTE_API_POST_URI = "https://jsonplaceholder.typicode.com/posts/";
	private final static Integer ID = 1;
	private final static Integer ID_NOT_EXISTS = 12345678;
	private final static Post POST = Post.builder().id(ID).body("test body").userId(ID).title("test title").build();
	private final static Post UPDATE_POST = Post.builder().id(ID).body("update body").userId(ID).title("update title").build();
	private final static HttpEntity<String> NO_BODY_ENTITY = new ApiRequestEntityBuilder().noBodyRequestEntity(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
	private final static HttpEntity<Post> POST_ENTITY = new ApiRequestEntityBuilder().postRequestEntity(POST, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);
	private final static HttpEntity<Post> POST_ENTITY_UPDATE = new ApiRequestEntityBuilder().postRequestEntity(UPDATE_POST, MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON);

	@Test
	public void testGetPosts() {
		Post[] postArray = { POST };
		when(restTemplate.exchange(REMOTE_API_POST_URI, HttpMethod.GET, NO_BODY_ENTITY, Post[].class)).thenReturn(new ResponseEntity<Post[]>(postArray, HttpStatus.OK));

		List<Post> createdList = postService.getPosts();

		assertEquals(Arrays.asList(postArray), createdList);

		verify(restTemplate, times(1)).exchange(REMOTE_API_POST_URI, HttpMethod.GET, NO_BODY_ENTITY, Post[].class);
	}

	@Test
	public void testGetPostById() {
		when(restTemplate.exchange(REMOTE_API_POST_URI + ID, HttpMethod.GET, NO_BODY_ENTITY, Post.class)).thenReturn(new ResponseEntity<Post>(POST, HttpStatus.OK));

		Post post = postService.getPostById(ID);

		assertEquals(POST, post);

		verify(restTemplate, times(1)).exchange(REMOTE_API_POST_URI + ID, HttpMethod.GET, NO_BODY_ENTITY, Post.class);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testGetPostByIdWhenPostNotExists() {
		when(restTemplate.exchange(REMOTE_API_POST_URI + ID_NOT_EXISTS, HttpMethod.GET, NO_BODY_ENTITY, Post.class)).thenThrow(ResourceNotFoundException.class);

		postService.getPostById(ID_NOT_EXISTS);

		verify(restTemplate, times(1)).exchange(REMOTE_API_POST_URI + ID, HttpMethod.GET, NO_BODY_ENTITY, Post.class);

	}

	@Test
	public void testCreatePost() {
		when(restTemplate.exchange(REMOTE_API_POST_URI, HttpMethod.POST, POST_ENTITY, Post.class)).thenReturn(new ResponseEntity<Post>(POST, HttpStatus.CREATED));

		Post post = postService.createPost(POST);

		assertEquals(POST, post);

		verify(restTemplate, times(1)).exchange(REMOTE_API_POST_URI, HttpMethod.POST, POST_ENTITY, Post.class);
	}

	@Test
	public void testUpdatePostWhenPostExists() {
		when(restTemplate.exchange(REMOTE_API_POST_URI + ID, HttpMethod.GET, NO_BODY_ENTITY, Post.class)).thenReturn(new ResponseEntity<Post>(POST, HttpStatus.OK));
		when(restTemplate.exchange(REMOTE_API_POST_URI + ID, HttpMethod.PUT, POST_ENTITY_UPDATE, Post.class)).thenReturn(new ResponseEntity<Post>(UPDATE_POST, HttpStatus.OK));

		Post post = postService.updatePost(ID, UPDATE_POST);

		assertEquals(UPDATE_POST, post);

		verify(restTemplate, times(1)).exchange(REMOTE_API_POST_URI + ID, HttpMethod.GET, NO_BODY_ENTITY, Post.class);
		verify(restTemplate, times(1)).exchange(REMOTE_API_POST_URI + ID, HttpMethod.PUT, POST_ENTITY_UPDATE, Post.class);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testUpdatePostWhenPostNotExists() {
		when(restTemplate.exchange(REMOTE_API_POST_URI + ID_NOT_EXISTS, HttpMethod.GET, NO_BODY_ENTITY, Post.class)).thenThrow(ResourceNotFoundException.class);

		postService.updatePost(ID_NOT_EXISTS, UPDATE_POST);
	}

	@Test
	public void testDeletePostWhenPostExists() {
		when(restTemplate.exchange(REMOTE_API_POST_URI + ID, HttpMethod.GET, NO_BODY_ENTITY, Post.class)).thenReturn(new ResponseEntity<Post>(POST, HttpStatus.OK));
		when(restTemplate.exchange(REMOTE_API_POST_URI + ID, HttpMethod.DELETE, NO_BODY_ENTITY, Post.class)).thenReturn(new ResponseEntity<Post>(POST, HttpStatus.OK));

		Integer deletePostId = postService.deletePost(ID);

		assertEquals(ID, deletePostId);

		verify(restTemplate, times(1)).exchange(REMOTE_API_POST_URI + ID, HttpMethod.GET, NO_BODY_ENTITY, Post.class);
		verify(restTemplate, times(1)).exchange(REMOTE_API_POST_URI + ID, HttpMethod.DELETE, NO_BODY_ENTITY, Post.class);
	}

	@Test(expected = ResourceNotFoundException.class)
	public void testDeletePostWhenPostNotExists() {
		when(restTemplate.exchange(REMOTE_API_POST_URI + ID_NOT_EXISTS, HttpMethod.GET, NO_BODY_ENTITY, Post.class)).thenThrow(ResourceNotFoundException.class);

		postService.deletePost(ID_NOT_EXISTS);
	}

}
