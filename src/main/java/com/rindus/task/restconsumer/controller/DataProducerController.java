package com.rindus.task.restconsumer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rindus.task.restconsumer.exception.ConcurentCallException;
import com.rindus.task.restconsumer.model.Comment;
import com.rindus.task.restconsumer.model.ConsumedData;
import com.rindus.task.restconsumer.model.Post;
import com.rindus.task.restconsumer.service.DataProducerService;

@RestController
@RequestMapping({ "/data" })
public class DataProducerController {

	@Autowired
	private DataProducerService dataProducerService;

	@PostMapping(path = "/posts/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ConsumedData getPostsWithComments(@Valid @RequestBody List<Integer> idList) throws ConcurentCallException {

		ConsumedData consumedData = new ConsumedData();
		List<Post> producedPosts = (List<Post>) dataProducerService.produceJsonData(idList, new Post());
		consumedData.setPosts(producedPosts);

		List<Comment> producedComments = (List<Comment>) dataProducerService.produceJsonData(idList, new Comment());
		consumedData.setComments(producedComments);

		return consumedData;
	}
}

