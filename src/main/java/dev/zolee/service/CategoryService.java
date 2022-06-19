package dev.zolee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.zolee.dto.CategoryRequestDto;
import dev.zolee.entity.BlogCategory;
import dev.zolee.entity.Category;
import dev.zolee.entity.Label;
import dev.zolee.repository.BlogCategoryRepository;
import dev.zolee.repository.CategoryRepository;
import dev.zolee.repository.LabelRepository;

@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final LabelRepository labelRepository;
	private final BlogCategoryRepository blogCategoryRepository;

	public CategoryService(CategoryRepository categoryRepository, 
							LabelRepository labelRepository, 
							BlogCategoryRepository blogCategoryRepository) {
		this.categoryRepository = categoryRepository;
		this.labelRepository = labelRepository;
		this.blogCategoryRepository = blogCategoryRepository;
	}

	public ResponseEntity<?> getCategory(long id) {
		Category category = categoryRepository.findById(id).orElse(null);
		if(category!=null) {
			return new ResponseEntity<>(category, HttpStatus.OK);
		}
		return new ResponseEntity<>("Category not found by id: " + id, HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<?> addCategory(CategoryRequestDto categoryRequestDto) {
		List<Category> categories = categoryRepository.findByCategoryName(categoryRequestDto.getCategoryName());
		if(categories!=null && categories.size()>0) {
			return new ResponseEntity<>("The Category is available!", HttpStatus.NOT_ACCEPTABLE);
		}
		Category category = new Category();
		category.setCategoryName(categoryRequestDto.getCategoryName());
		categoryRepository.save(category);
		List<String> labelNames = categoryRequestDto.getCategoryLabel();
		List<Label> labels = new ArrayList<>();
		if(checkLabelsLengthNotappropriate(labelNames)) {
			return new ResponseEntity<>("List of labels contain not appropriate length label!",
							HttpStatus.NOT_ACCEPTABLE);
		}
		labels = saveLabelsFromStrings(labelNames, category);
		category.setLabels(labels);
		return new ResponseEntity<>(category, HttpStatus.OK);
	}

	public ResponseEntity<?> updateCategory(CategoryRequestDto categoryRequestDto, long id) {
		Category category = categoryRepository.findById(id).orElse(null);
		List<Label> tempLabels = category.getLabels();
		tempLabels.forEach( tempLabel -> {
			labelRepository.delete(tempLabel);
		});
		List<String> labelNames = categoryRequestDto.getCategoryLabel();
		List<Label> labels = new ArrayList<>();
		if(checkLabelsLengthNotappropriate(labelNames)) {
			return new ResponseEntity<>("List of labels contain not appropriate length label!", 
							HttpStatus.NOT_ACCEPTABLE);
		}
		labels = saveLabelsFromStrings(labelNames, category);
		if(category!=null) {
			category.setCategoryName(categoryRequestDto.getCategoryName());
			category.setLabels(labels);
			categoryRepository.save(category);
		}
		return new ResponseEntity<>("Category not found to update by id: " + id, HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<?> deleteCategory(long id) {
		Category category = categoryRepository.findById(id).orElse(null);
		if(category!=null) {
			List<BlogCategory> blogCategories = blogCategoryRepository.findByCategory(category);
			blogCategories.forEach( blogCategory -> {
				blogCategoryRepository.delete(blogCategory);
			});
			List<Label> labels = category.getLabels();
			labels.forEach( label -> {
				labelRepository.delete(label);
			});
			categoryRepository.delete(category);
			return new ResponseEntity<>("Category succesfully deleted!", HttpStatus.OK);
		}
		return new ResponseEntity<>("Category not found by id: " + id, HttpStatus.NO_CONTENT);
	}

	private boolean checkLabelsLengthNotappropriate(List<String> tempLabels) {
		for (String tempString : tempLabels) {
			if(tempString.length()<3 || tempString.length()>10) {
				return true;
			}
		}
		return false;
	}
	
	private List<Label> saveLabelsFromStrings(List<String> labelNames, Category category) {
		List<Label> labels = new ArrayList<>();
		labelNames.forEach( tempLabelName -> {
			Label tempLabel = new Label();
			tempLabel.setCategoryLabel(tempLabelName);
			tempLabel.setCategory(category);
			labelRepository.save(tempLabel);
			labels.add(tempLabel);
		});
		return labels;
	}
	
}
