package com.gu.dscatalog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gu.dscatalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query("SELECT obj FROM Product obj JOIN FETCH obj.categories WHERE obj IN :product")
	List<Product> findProductCategory(List<Product> product);
	
}
