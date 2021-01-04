package com.rindus.task.restconsumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
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

import com.rindus.task.restconsumer.controller.DataProducerController;
import com.rindus.task.restconsumer.exception.ConcurentCallException;
import com.rindus.task.restconsumer.model.BaseFields;
import com.rindus.task.restconsumer.model.Comment;
import com.rindus.task.restconsumer.model.Post;
import com.rindus.task.restconsumer.service.DataProducerServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class DataProducerControllerTest {

	@InjectMocks
	private DataProducerController dataProducerController;

	@Mock
	private DataProducerServiceImpl dataProducerService;

	private final static Integer ID = 1;
	private final static Post POST = Post.builder().id(ID).body("test body").userId(ID).title("test title").build();
	private final static Comment COMMENT = Comment.builder().id(ID).body("test post body " + ID).postId(ID).email("test email " + ID).name("test name " + ID).build();
	private final static List<? extends BaseFields> POST_LIST = Arrays.asList(POST);
	private final static List<? extends BaseFields> COMMENT_LIST = Arrays.asList(COMMENT);
	private final static List<Integer> ID_LIST = Arrays.asList(1);


	@Test
	public void testGetAllPosts() throws ConcurentCallException {
		when(dataProducerService.produceJsonData(anyList(), any(Post.class))).thenAnswer(a -> POST_LIST);
		when(dataProducerService.produceJsonData(anyList(), any(Comment.class))).thenAnswer(a -> COMMENT_LIST);

		dataProducerController.getPostsWithComments(ID_LIST);

		verify(dataProducerService, times(1)).produceJsonData(anyList(), any(Post.class));
		verify(dataProducerService, times(1)).produceJsonData(anyList(), any(Comment.class));
	}
}
