package com.rindus.task.restconsumer.controller;

import java.util.List;
import java.util.stream.Collectors;

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

	private DataProducerService dataProducerService;

	@Autowired
	public DataProducerController(DataProducerService dataProducerService) {
		this.dataProducerService = dataProducerService;
	}

	@PostMapping(path = "/posts/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ConsumedData getPostsWithComments(@Valid @RequestBody List<Integer> idList) throws ConcurentCallException {

		idList= idList.stream().distinct().collect(Collectors.toList());
		ConsumedData consumedData = new ConsumedData();
		List<Post> producedPosts = (List<Post>) dataProducerService.produceJsonData(idList, new Post());
		consumedData.setPosts(producedPosts);

		List<Comment> producedComments = (List<Comment>) dataProducerService.produceJsonData(idList, new Comment());
		consumedData.setComments(producedComments);

		return consumedData;
	}
}
