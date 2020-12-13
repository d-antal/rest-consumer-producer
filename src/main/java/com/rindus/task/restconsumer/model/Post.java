package com.rindus.task.restconsumer.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Post extends BaseFields {
	private Long userId;
	private String title;
}

