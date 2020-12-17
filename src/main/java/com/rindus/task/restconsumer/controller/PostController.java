package com.rindus.task.restconsumer.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rindus.task.restconsumer.exception.ResourceNotFoundException;
import com.rindus.task.restconsumer.model.Post;
import com.rindus.task.restconsumer.service.PostService;

@RestController
@RequestMapping({ "/posts" })
public class PostController {
   
	@Autowired
	private PostService postService;

	@GetMapping()
	public List<Post> getAllPost() {
		return postService.getPosts();
	}

	@GetMapping(path = "/{postId}")
	public Post getPostById(@PathVariable(value = "postId") Integer postId) throws ResourceNotFoundException {
		return postService.getPostById(postId);
	}

	@PostMapping()
	public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
		Post consumedPost = postService.createPost(post);
		return new ResponseEntity<Post>(consumedPost, HttpStatus.CREATED);
	}

	@PutMapping(path = "/{postId}")
	public Post updatePost(@PathVariable(value = "postId") Integer postId, @Valid @RequestBody Post post) throws ResourceNotFoundException {
		return postService.updatePost(postId, post);
	}

	@DeleteMapping(path = "/{postId}")
	public void deletePost(@PathVariable(value = "postId") Integer postId) throws ResourceNotFoundException {
		postService.deletePost(postId);
	}

}
