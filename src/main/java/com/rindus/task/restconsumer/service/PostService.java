package com.rindus.task.restconsumer.service;

import java.util.List;

import com.rindus.task.restconsumer.model.Post;

public interface PostService {

	List<Post> getPosts();

	Post getPostById(Integer id);

	Post createPost(Post post);

	Post updatePost(Integer id, Post post);

	void deletePost(Integer id);
}
