package dev.zolee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.zolee.dto.AddBlogCategoryRequestDto;
import dev.zolee.service.BlogCategoryService;

@RestController
@RequestMapping("/api/blogcategory")
public class BlogCategoryController {

	private final BlogCategoryService blogCategoryService;
	
	public BlogCategoryController(BlogCategoryService blogCategoryService) {
		this.blogCategoryService = blogCategoryService;
	}

	@PostMapping()
	public ResponseEntity<?> addBlogCategory(@RequestBody AddBlogCategoryRequestDto addBlogCategoryRequestDto) {
		return blogCategoryService.addBlogCategory(addBlogCategoryRequestDto);
	}
	
	@DeleteMapping("/{blogid}/{categoryid}")
	public ResponseEntity<?> deleteBlogCategory(@PathVariable long blogid, @PathVariable long categoryid) {
		return blogCategoryService.deleteBlogCategory(blogid, categoryid);
	}
	
}
