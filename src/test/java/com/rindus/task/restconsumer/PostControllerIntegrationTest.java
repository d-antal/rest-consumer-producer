package com.rindus.task.restconsumer;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rindus.task.restconsumer.model.Post;
import com.rindus.task.restconsumer.service.PostServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PostServiceImpl postService;

	private static final ObjectMapper OM = new ObjectMapper();
	private final static String BASE_URI_POST = "https://jsonplaceholder.typicode.com/posts/";
	private final static Integer ID = 1;
	private final static Integer ID_2 = 1;
	private final static Post POST = Post.builder().id(ID).body("test body").userId(ID).title("test title").build();
	private final static Post POST_2 = Post.builder().id(ID_2).body("test body2").userId(ID_2).title("test title2").build();
	private final static List<Post> POST_LIST = Arrays.asList(POST, POST_2);

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetPostById() throws Exception {
		when(postService.getPostById(ID)).thenReturn(POST);

		mockMvc.perform(get(BASE_URI_POST + ID).content(OM.writeValueAsString(POST)).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.body", is(POST.getBody()))).andExpect(jsonPath("$.id", is(ID))).andExpect(jsonPath("$.userId", is(POST.getUserId())))
				.andExpect(jsonPath("$.title", is(POST.getTitle())));

		verify(postService, times(1)).getPostById(ID);
	}

	@Test
	public void testGetPosts() throws Exception {
		when(postService.getPosts()).thenReturn(POST_LIST);

		mockMvc.perform(get(BASE_URI_POST).content(OM.writeValueAsString(POST_LIST)).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].body", is(POST.getBody()))).andExpect(jsonPath("$[0].id", is(ID))).andExpect(jsonPath("$[0].userId", is(POST.getUserId())))
				.andExpect(jsonPath("$[0].title", is(POST.getTitle()))).andExpect(jsonPath("$[1].body", is(POST_2.getBody()))).andExpect(jsonPath("$[1].id", is(ID_2)))
				.andExpect(jsonPath("$[1].userId", is(POST_2.getUserId()))).andExpect(jsonPath("$[1].title", is(POST_2.getTitle())));

		verify(postService, times(1)).getPosts();
	}

	@Test
	public void testCreatePost() throws Exception {
		when(postService.createPost(POST)).thenReturn(POST);

		mockMvc.perform(post(BASE_URI_POST).content(OM.writeValueAsString(POST)).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
				.andExpect(jsonPath("$.body", is(POST.getBody()))).andExpect(jsonPath("$.id", is(ID))).andExpect(jsonPath("$.userId", is(POST.getUserId())))
				.andExpect(jsonPath("$.title", is(POST.getTitle())));

		verify(postService, times(1)).createPost(POST);
	}

	@Test
	public void testUpdatePost() throws Exception {
		when(postService.getPostById(ID)).thenReturn(POST);
		when(postService.updatePost(ID, POST_2)).thenReturn(POST_2);

		mockMvc.perform(put(BASE_URI_POST + ID).content(OM.writeValueAsString(POST_2)).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.body", is(POST_2.getBody()))).andExpect(jsonPath("$.id", is(POST_2.getId()))).andExpect(jsonPath("$.userId", is(POST_2.getUserId())))
				.andExpect(jsonPath("$.title", is(POST_2.getTitle())));

		verify(postService, times(1)).updatePost(ID, POST_2);
	}

	@Test
	public void testDeletePost() throws Exception {
		when(postService.getPostById(ID)).thenReturn(POST);
		when(postService.deletePost(ID)).thenReturn(ID);

		mockMvc.perform(delete(BASE_URI_POST + ID).content(OM.writeValueAsString(ID)).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		verify(postService, times(1)).deletePost(ID);
	}

	private static HttpHeaders createHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

}
