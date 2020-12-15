package com.rindus.task.restconsumer.service;

import java.util.List;

import com.rindus.task.restconsumer.exception.ResourceNotFoundException;
import com.rindus.task.restconsumer.model.Post;

public interface PostService {

	List<Post> getPosts();

	Post getPostById(Integer id) throws ResourceNotFoundException;

	Post createPost(Post post);

	Post updatePost(Integer id, Post post) throws ResourceNotFoundException;

	void deletePost(Integer id) throws ResourceNotFoundException;
}
