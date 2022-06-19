package dev.zolee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.zolee.dto.BlogRequestDto;
import dev.zolee.service.BlogService;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

	private final BlogService blogService;
	
	public BlogController(BlogService blogService) {
		this.blogService = blogService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getBlog(@PathVariable long id) {
		return blogService.getBlog(id);
	}
	
	@PostMapping()
	public ResponseEntity<?> addBlog(@RequestBody BlogRequestDto BlogRequestDto) {
		return blogService.addBlog(BlogRequestDto);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateBlog(@RequestBody BlogRequestDto blogRequestDto, @PathVariable long id) {
		return blogService.updateBlog(blogRequestDto, id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBlog(@PathVariable long id) {
		return blogService.deleteBlog(id);
	}
	
	@GetMapping("/findByLabel")
	public ResponseEntity<?> findByLabel(@RequestParam("label") String label, 
										@RequestParam("page") int page, 
										@RequestParam("size") int size) {
		return blogService.findByLabel(label, page, size);
	}
	
}
