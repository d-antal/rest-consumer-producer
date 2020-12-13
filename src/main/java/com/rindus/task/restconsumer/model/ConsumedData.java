package com.rindus.task.restconsumer.model;

import java.util.List;

import lombok.Data;

@Data
public class ConsumedData {
	private List<Post> posts;
	private List<Comment> comments;	
}



