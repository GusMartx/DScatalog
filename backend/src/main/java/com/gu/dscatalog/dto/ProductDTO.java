package com.gu.dscatalog.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.gu.dscatalog.entities.Product;

public class ProductDTO {

	private Long id;
	@Size(min = 2, max = 80, message = "Nome deve conter entre 2 e 80 caracteres")
	@NotBlank(message = "Campo requirido")
	private String name;
	@Size(min = 2, max = 80, message = "Descrição deve conter entre 2 e 80 caracteres")
	@NotBlank(message = "Campo requirido")
	private String description;
	@Positive(message = "Preço deve ser maior que zero")
	private Double price;
	private String imgUrl;
	@PastOrPresent(message = "Data não deve ser futura")
	private Instant date;

	private List<CategoryDTO> categories = new ArrayList<>();

	public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
		this.date = date;
	}

	public ProductDTO(Product entity) {
		id = entity.getId();
		name = entity.getName();
		description = entity.getDescription();
		price = entity.getPrice();
		imgUrl = entity.getImgUrl();
		categories = entity.getCategories().stream().map(x -> new CategoryDTO(x)).toList();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Double getPrice() {
		return price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public Instant getDate() {
		return date;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}

}
