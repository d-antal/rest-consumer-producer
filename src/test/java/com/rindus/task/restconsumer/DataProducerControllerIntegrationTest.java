package com.rindus.task.restconsumer;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rindus.task.restconsumer.model.Comment;
import com.rindus.task.restconsumer.model.Post;
import com.rindus.task.restconsumer.service.DataProducerServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DataProducerControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DataProducerServiceImpl dataProducerService;

	private static final ObjectMapper OM = new ObjectMapper();
	private final static String PRODUCE_POSTS_COMMENTS_DATA_URL = "http://localhost:8080/data/posts/comments";

	private final static Integer ID = 1;
	private final static Post POST = Post.builder().id(ID).body("test body").userId(ID).title("test title").build();
	private final static Comment COMMENT = Comment.builder().id(ID).body("test post body " + ID).postId(ID).email("test email " + ID).name("test name " + ID).build();
	private final static List<Post> POST_LIST = Arrays.asList(POST);
	private final static List<Comment> COMMENT_LIST = Arrays.asList(COMMENT);
	private final static List<Integer> ID_LIST = Arrays.asList(1);

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetPostsWithComments() throws Exception {
		when(dataProducerService.produceJsonData(anyList(), any(Post.class))).thenAnswer(a -> POST_LIST);
		when(dataProducerService.produceJsonData(anyList(), any(Comment.class))).thenAnswer(a -> COMMENT_LIST);

		mockMvc.perform(post(PRODUCE_POSTS_COMMENTS_DATA_URL).content(OM.writeValueAsString(ID_LIST)).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.posts[0].body", is(POST.getBody())))
				.andExpect(jsonPath("$.posts[0].id", is(POST.getId())))
				.andExpect(jsonPath("$.posts[0].userId", is(POST.getUserId())))
				.andExpect(jsonPath("$.posts[0].title", is(POST.getTitle())))
				.andExpect(jsonPath("$.comments[0].body", is(COMMENT.getBody())))
				.andExpect(jsonPath("$.comments[0].id", is(COMMENT.getId())))
				.andExpect(jsonPath("$.comments[0].postId", is(COMMENT.getPostId())))
				.andExpect(jsonPath("$.comments[0].name", is(COMMENT.getName())))
				.andExpect(jsonPath("$.comments[0].email", is(COMMENT.getEmail())));

		verify(dataProducerService, times(1)).produceJsonData(anyList(), any(Post.class));
		verify(dataProducerService, times(1)).produceJsonData(anyList(), any(Comment.class));
	}

}
