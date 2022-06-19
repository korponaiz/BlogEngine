package dev.zolee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.zolee.dto.CategoryRequestDto;
import dev.zolee.service.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getCategory(@PathVariable long id) {
		return categoryService.getCategory(id);
	}
	
	@PostMapping()
	public ResponseEntity<?> addCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
		return categoryService.addCategory(categoryRequestDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@RequestBody CategoryRequestDto categoryRequestDto, @PathVariable long id) {
		return categoryService.updateCategory(categoryRequestDto, id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable long id) {
		return categoryService.deleteCategory(id);
	}
	
}
