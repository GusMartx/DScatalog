package com.gu.dscatalog.tests;

import java.time.Instant;

import com.gu.dscatalog.dto.ProductDTO;
import com.gu.dscatalog.entities.Category;
import com.gu.dscatalog.entities.Product;

public class Factory {

	public static Product createProduct() {
		Product product = new Product(1L, "Phone", "Good Phone", 8000.0, "http://img.com/img.png", Instant.parse("2020-10-20T03:00:00Z"));
		product.getCategories().add(createCategory());
		return product;
	}
	
	public static ProductDTO createProductDTO() {
		Product product = createProduct();
		return new ProductDTO(product);
	}
	
	public static Category createCategory() {
		Category category = new Category(1L, "Electronics");
		return category;
	}
}
