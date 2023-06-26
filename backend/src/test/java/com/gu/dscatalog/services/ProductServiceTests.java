package com.gu.dscatalog.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gu.dscatalog.dto.ProductDTO;
import com.gu.dscatalog.entities.Category;
import com.gu.dscatalog.entities.Product;
import com.gu.dscatalog.repositories.CategoryRepository;
import com.gu.dscatalog.repositories.ProductRepository;
import com.gu.dscatalog.services.exceptions.DatabaseException;
import com.gu.dscatalog.services.exceptions.ResourceNotFoundException;
import com.gu.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {

	@InjectMocks
	private ProductService service;
	@Mock
	private ProductRepository repository;
	@Mock
	private CategoryRepository categoryRepository;

	private long existingId;
	private long nonExistingId;
	private long dependentId;
	private Product product;
	private Category category;
	private ProductDTO productDto;
	private PageImpl<Product> page;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		product = Factory.createProduct();
		page = new PageImpl<>(List.of(product));
		category = Factory.createCategory();
		productDto = Factory.createProductDTO();

		when(repository.findAll((Pageable) any())).thenReturn(page);

		when(repository.save(any())).thenReturn(product);

		when(repository.findById(existingId)).thenReturn(Optional.of(product));
		when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		when(repository.getOne(existingId)).thenReturn(product);
		when(repository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);

		when(categoryRepository.getOne(existingId)).thenReturn(category);
		when(categoryRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);

		doNothing().when(repository).deleteById(existingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}

	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {

		ProductDTO result = service.update(existingId, productDto);

		Assertions.assertNotNull(result);
		verify(repository).getOne(existingId);
	}

	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.update(nonExistingId, productDto);
		});

	}

	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {

		ProductDTO result = service.findById(existingId);

		Assertions.assertNotNull(result);
		verify(repository).findById(existingId);
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(nonExistingId);
		});

	}

	@Test
	public void findAllShouldReturnPage() {

		Pageable pageable = PageRequest.of(0, 10);

		Page<ProductDTO> result = service.findAll(pageable);

		Assertions.assertNotNull(result);
		verify(repository).findAll(pageable);
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {

		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});

	}

	@Test
	public void deleteShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		verify(repository, times(1)).deleteById(existingId);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});

		verify(repository, times(1)).deleteById(nonExistingId);
	}

}
