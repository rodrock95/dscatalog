package com.devsuperior.dscatalog.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class) // teste unitario sem relacao com repository
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
	private PageImpl<Product> page;
	private Product product;
	private ProductDTO dto;
	private Category category;

	@BeforeEach
	void setUp() throws Exception {
		/*
		 * existingId = 1L; nonExistingId = 1000L; dependentId = 4L;
		 */
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;
		product = Factory.createProduct();
		category = Factory.createCategory();
		dto = Factory.createProductDTO();
		page = new PageImpl<>(List.of(product));

		// simular o comportamento do findAll do repository
		Mockito.when(repository.findAll((Pageable) any())).thenReturn(page);

		// simular o comportamento do save do repository
		Mockito.when(repository.save(any())).thenReturn(product);

		// simular o comportamento do findById quando id existe
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(product));

		// simular o comportamento do findById quando id não existe
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.when(repository.find(any(), any(), any())).thenReturn(page);
		
		// simular o comportamento do update quando id existe
		Mockito.when(repository.getOne(existingId)).thenReturn(product);
		
		// simular o comportamento do update quando id não existir
		Mockito.when(repository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);
		
		// simular o comportamento do update quando id existir para category
		Mockito.when(categoryRepository.getOne(existingId)).thenReturn(category);
		
		// simular o comportamento do update quando id não existir para category
		Mockito.when(categoryRepository.getOne(nonExistingId)).thenThrow(EntityNotFoundException.class);

		// comportamentos do objeto mockado
		// Mockito.doNothing().when(repository).deleteById(existingId);
		doNothing().when(repository).deleteById(existingId);

		// Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);

		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}

	@Test
	public void findAllPagedShouldReturnPage() {

		Pageable pageable = PageRequest.of(0, 10);

		Page<ProductDTO> result = service.findAllPaged(0L, "", pageable);

		Assertions.assertNotNull(result);
		
		//Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {

		Assertions.assertThrows(DatabaseException.class, () -> {
			service.delete(dependentId);
		});

		// Vai verificar se o metodo deleteById foi chamado nessa acao que o eu coloquei
		// no meu teste
		// Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
		verify(repository, times(1)).deleteById(dependentId);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(nonExistingId);
		});

		// Vai verificar se o metodo deleteById foi chamado nessa acao que o eu coloquei
		// no meu teste
		// Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
		verify(repository, times(1)).deleteById(nonExistingId);
	}

	@Test
	public void deleteShouldDoNothingWhenIdExist() {

		Assertions.assertDoesNotThrow(() -> {
			service.delete(existingId);
		});

		// Vai verificar se o metodo deleteById foi chamado nessa acao que o eu coloquei
		// no meu teste
		// Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
		verify(repository, times(1)).deleteById(existingId);
	}

	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists() {
		
		ProductDTO dto = service.findById(existingId);

		Assertions.assertNotNull(dto);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class,() -> {
			service.findById(nonExistingId);
		});
		
		}

	@Test
	public void updateShouldReturnProductDTOWhenIdExists() {
		
		ProductDTO result = service.update(existingId, dto);
		
		Assertions.assertNotNull(result);
				
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResourceNotFoundException.class,() -> {
			service.update(nonExistingId, dto);
		});
		
		}
}


