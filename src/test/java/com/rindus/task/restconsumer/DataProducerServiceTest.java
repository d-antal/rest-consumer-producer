package com.rindus.task.restconsumer;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.rindus.task.restconsumer.exception.ConcurentCallException;
import com.rindus.task.restconsumer.model.Comment;
import com.rindus.task.restconsumer.model.Post;
import com.rindus.task.restconsumer.service.CommentService;
import com.rindus.task.restconsumer.service.DataProducerService;
import com.rindus.task.restconsumer.service.PostService;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestConsumerApplication.class)
public class DataProducerServiceTest {

	@Autowired
	private PostService postService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private DataProducerService dataProducerService;

	private final static List<Integer> ID_LIST = Arrays.asList(0, 1, 2, 3, 4);
	private final static List<Post> POST_lIST = createPostList();
	private final static List<Comment> COMMENT_LIST = createCommentList();

	@Test
	public void testProductJsonDataWhenPost() throws ConcurentCallException {
		ID_LIST.forEach(id -> when(postService.getPostById(ID_LIST.get(id))).thenReturn(POST_lIST.get(id)));
		List<Post> posts = (List<Post>) dataProducerService.produceJsonData(ID_LIST, new Post());

		assertTrue(posts.containsAll(POST_lIST));

		verify(postService, times(ID_LIST.size())).getPostById(anyInt());
	}

	@Test
	public void testProductJsonDataWhenComment() throws ConcurentCallException {
		ID_LIST.forEach(id -> when(commentService.getCommentById(ID_LIST.get(id))).thenReturn(COMMENT_LIST.get(id)));
		List<Comment> comments = (List<Comment>) dataProducerService.produceJsonData(ID_LIST, new Comment());

		assertTrue(comments.containsAll(COMMENT_LIST));

		verify(commentService, times(ID_LIST.size())).getCommentById(anyInt());
	}

	private static List<Post> createPostList() {
		List<Post> postList = new ArrayList<Post>();
		ID_LIST.forEach(id -> postList.add(Post.builder().id(id).body("test post body " + id).userId(id).title("test post title " + id).build()));
		return postList;
	}

	private static List<Comment> createCommentList() {
		List<Comment> commentList = new ArrayList<Comment>();
		ID_LIST.forEach(id -> commentList.add(Comment.builder().id(id).body("test post body " + id).postId(id).email("test email " + id).name("test name " + id).build()));
		return commentList;
	}
}
