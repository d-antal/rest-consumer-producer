package com.rindus.task.restconsumer;

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

import com.rindus.task.restconsumer.controller.PostController;
import com.rindus.task.restconsumer.model.Post;
import com.rindus.task.restconsumer.service.PostServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

	@InjectMocks
	private PostController postController;

	@Mock
	private PostServiceImpl postService;

	private final static Integer ID = 1;
	private final static Integer ID_2 = 1;
	private final static Post POST = Post.builder().id(ID).body("test body").userId(ID).title("test title").build();
	private final static Post POST_2 = Post.builder().id(ID_2).body("test body2").userId(ID_2).title("test title2").build();
	private final static List<Post> POST_LIST = Arrays.asList(POST, POST_2);

	@Test
	public void testGetAllPosts() {
		when(postService.getPosts()).thenReturn(POST_LIST);

		postController.getAllPost();

		verify(postService, times(1)).getPosts();
	}

	@Test
	public void testGetPostById() {
		when(postService.getPostById(ID)).thenReturn(POST);

		postController.getPostById(ID);

		verify(postService, times(1)).getPostById(ID);
	}

	@Test
	public void testUpdatePost() {
		when(postService.updatePost(ID, POST_2)).thenReturn(POST_2);

		postController.updatePost(ID, POST_2);

		verify(postService, times(1)).updatePost(ID, POST_2);
	}

	@Test
	public void testDeletePost() {
		when(postService.deletePost(ID)).thenReturn(ID);

		postController.deletePost(ID);

		verify(postService, times(1)).deletePost(ID);
	}
}
