package com.rindus.task.restconsumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.rindus.task.restconsumer.model.Comment;
import com.rindus.task.restconsumer.service.CommentServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private CommentServiceImpl commentService;

	private final static String REMOTE_API_COMMENTS_URI = "https://jsonplaceholder.typicode.com/comments/";
	private final static Integer ID = 1;
	private final static Comment COMMENT = Comment.builder().id(ID).body("test post body " + ID).postId(ID).email("test email " + ID).name("test name " + ID).build();
	private final static HttpEntity<String> STRING_ENTITY = new HttpEntity<String>(createHeader());

	@Test
	public void testGetCommentById() {
		when(restTemplate.exchange(REMOTE_API_COMMENTS_URI + ID, HttpMethod.GET, STRING_ENTITY, Comment.class)).thenReturn(new ResponseEntity<Comment>(COMMENT, HttpStatus.OK));

		Comment comment = commentService.getCommentById(ID);

		assertEquals(COMMENT, comment);

		verify(restTemplate, times(1)).exchange(REMOTE_API_COMMENTS_URI + ID, HttpMethod.GET, STRING_ENTITY, Comment.class);
	}

	private static HttpHeaders createHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
