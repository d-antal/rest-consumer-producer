package com.rindus.task.restconsumer.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rindus.task.restconsumer.exception.ConcurentCallException;
import com.rindus.task.restconsumer.model.BaseFields;
import com.rindus.task.restconsumer.model.DataProducerConstants;
import com.rindus.task.restconsumer.model.Post;

@Service
public class DataProducerServiceImpl implements DataProducerService {

	private PostService postService;

	private CommentService commentService;

	@Autowired
	public DataProducerServiceImpl(PostService postService, CommentService commentService) {
		this.postService = postService;
		this.commentService = commentService;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(DataProducerService.class);

	public List<? extends BaseFields> produceJsonData(List<Integer> idList, BaseFields input) throws ConcurentCallException {
		AtomicInteger start = new AtomicInteger(0);
		ExecutorService executor = Executors.newFixedThreadPool(idList.size());
		List<Callable<BaseFields>> callableList = new ArrayList<>();

		Callable<BaseFields> callableTask = () -> {
			int i = start.getAndIncrement();
			return input instanceof Post ? postService.getPostById(idList.get(i)) : commentService.getCommentById(idList.get(i));
		};

		for (int i = 1; i <= idList.size(); i++) {
			callableList.add(callableTask);
		}

		List<BaseFields> postList = new ArrayList<BaseFields>();
		try {
			List<Future<BaseFields>> futures = executor.invokeAll(callableList);
			for (Future<BaseFields> future : futures) {
				postList.add(future.get());
			}
		} catch (Exception e) {
			LOGGER.error(DataProducerConstants.CONCURENT_PROCESS_DATA_ERROR, e);
			throw new ConcurentCallException(DataProducerConstants.CONCURENT_PROCESS_DATA_ERROR);
		}
		executor.shutdown();
		return postList;
	}

}
