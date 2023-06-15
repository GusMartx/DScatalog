package com.gu.dscatalog.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.gu.dscatalog.entities.Category;

public class CategoryDTO {

	private Long id;
	@Size(min = 3, max = 30, message = "Nome deve conter entre 3 e 30 caracteres")
	@NotBlank(message = "Campo requirido")
	private String name;

	public CategoryDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public CategoryDTO(Category entity) {
		id = entity.getId();
		name = entity.getName();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
