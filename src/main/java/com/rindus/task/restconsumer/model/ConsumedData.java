package com.rindus.task.restconsumer.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsumedData {
	private List<Post> posts;
	private List<Comment> comments;	
}



