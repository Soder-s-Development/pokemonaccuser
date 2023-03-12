package com.juliano.app.builder;

import javax.validation.constraints.NotNull;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableBuilder {

	@NotNull
	private int currentPage;
	@NotNull
	private int size;
	private Boolean ascending;
	@Nullable
	private String fieldOrderBy;
	
	
	public PageRequest buildPage() {
		return PageRequest.of(currentPage, size, ascending ? Sort.Direction.ASC : Sort.Direction.DESC, fieldOrderBy == null ? "id" : fieldOrderBy);
	}
}
