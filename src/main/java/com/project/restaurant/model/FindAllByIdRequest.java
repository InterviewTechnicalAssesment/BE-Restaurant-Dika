package com.project.restaurant.model;

import java.util.Set;

import lombok.Data;

@Data
public class FindAllByIdRequest {
	
	private Set<Long> ids;

}
