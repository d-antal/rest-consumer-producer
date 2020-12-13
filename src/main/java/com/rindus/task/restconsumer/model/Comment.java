package com.rindus.task.restconsumer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Comment extends BaseFields {
	private Long postId;	
	private String name;
	private String email;
}

