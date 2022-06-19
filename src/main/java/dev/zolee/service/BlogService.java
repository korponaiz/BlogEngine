package dev.zolee.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.zolee.dto.BlogRequestDto;
import dev.zolee.entity.Blog;
import dev.zolee.entity.BlogCategory;
import dev.zolee.entity.Category;
import dev.zolee.entity.Label;
import dev.zolee.repository.BlogCategoryRepository;
import dev.zolee.repository.BlogPageableRepository;
import dev.zolee.repository.BlogRepository;
import dev.zolee.repository.LabelRepository;

@Service
public class BlogService {

	private final BlogRepository blogRepository;
	private final BlogCategoryRepository blogCategoryRepository;
	private final LabelRepository labelRepository;
	private final BlogPageableRepository blogPageableRepository;

	public BlogService(BlogRepository blogRepository, 
						BlogCategoryRepository blogCategoryRepository,
						LabelRepository labelRepository,
						BlogPageableRepository blogPageableRepository) {
		this.blogRepository = blogRepository;
		this.blogCategoryRepository = blogCategoryRepository;
		this.labelRepository = labelRepository;
		this.blogPageableRepository = blogPageableRepository;
	}

	public ResponseEntity<?> getBlog(long id) {
		Blog blog = blogRepository.findById(id).orElse(null);
		if(blog!=null) {
			return new ResponseEntity<>(blog, HttpStatus.OK);
		}
		return new ResponseEntity<>("Blog not found by id: " + id, HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<?> addBlog(BlogRequestDto blogRequestDto) {
		Blog blog = new Blog();
		blog.setBlogName(blogRequestDto.getBlogName());
		blog.setBlogText(blogRequestDto.getBlogText());
		blog.setModifyTime(LocalDateTime.now());
		blogRepository.save(blog);
		return new ResponseEntity<>(blog, HttpStatus.OK);
	}

	public ResponseEntity<?> updateBlog(BlogRequestDto blogRequestDto, long id) {
		Blog blog = blogRepository.findById(id).orElse(null);
		if(blog!=null) {
			blog.setBlogName(blogRequestDto.getBlogName());
			blog.setBlogText(blogRequestDto.getBlogText());
			blog.setModifyTime(LocalDateTime.now());
			blogRepository.save(blog);
			return new ResponseEntity<>("Blog succesfully updated!", HttpStatus.OK);
		}
		return new ResponseEntity<>("Blog not found to update by id: " + id, HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<?> deleteBlog(long id) {
		Blog blog = blogRepository.findById(id).orElse(null);
		if(blog!=null) {
			List<BlogCategory> blogCategories = blogCategoryRepository.findByBlog(blog);
			blogCategories.forEach( blogCategory -> {
				blogCategoryRepository.delete(blogCategory);
			});
			blogRepository.delete(blog);
			return new ResponseEntity<>("Blog succesfully deleted!", HttpStatus.OK);
		}
		return new ResponseEntity<>("Blog not found to delete by id: " + id, HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<?> findByLabel(String labelName, int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		List<Label> label = labelRepository.findByCategoryLabel(labelName);
		Set<Category> categories = label.stream().map(Label::getCategory).collect(Collectors.toSet());
		List<Long> blogIds = new ArrayList<>();
		categories.forEach( e -> {
			e.getBlogCategories().forEach( x -> blogIds.add(x.getBlog().getId()));
		});
		Page<Blog> blog = blogPageableRepository.findByIdIn(blogIds,paging);
		return new ResponseEntity<>(blog, HttpStatus.OK);
	}
	
}



