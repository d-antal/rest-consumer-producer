package com.rindus.task.restconsumer;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.rindus.task.restconsumer.controller.PostController;
import com.rindus.task.restconsumer.service.CommentService;
import com.rindus.task.restconsumer.service.CommentServiceImpl;
import com.rindus.task.restconsumer.service.DataProducerService;
import com.rindus.task.restconsumer.service.DataProducerServiceImpl;
import com.rindus.task.restconsumer.service.PostService;
import com.rindus.task.restconsumer.service.PostServiceImpl;

@Profile("test")
@Configuration
public class TestConfiguration {

	@Bean
	@Primary
	public PostService postService() {
		return Mockito.mock(PostServiceImpl.class);
	}

	@Bean
	@Primary
	public CommentService commentService() {
		return Mockito.mock(CommentServiceImpl.class);
	}

	@Bean
	@Primary
	public DataProducerService dataProducerService() {
		return new DataProducerServiceImpl(postService(), commentService());
	}

}
