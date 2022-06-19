package dev.zolee.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import dev.zolee.dto.AddBlogCategoryRequestDto;
import dev.zolee.entity.Blog;
import dev.zolee.entity.BlogCategory;
import dev.zolee.entity.Category;
import dev.zolee.repository.BlogCategoryRepository;
import dev.zolee.repository.BlogRepository;
import dev.zolee.repository.CategoryRepository;

@Service
public class BlogCategoryService {

	private final BlogCategoryRepository blogCategoryRepository;
	private final BlogRepository blogRepository;
	private final CategoryRepository categoryRepository;
	
	public BlogCategoryService(BlogCategoryRepository blogCategoryRepository,
								BlogRepository blogRepository,
								CategoryRepository categoryRepository) {
		this.blogCategoryRepository = blogCategoryRepository;
		this.blogRepository = blogRepository;
		this.categoryRepository = categoryRepository;
	}

	public ResponseEntity<?> addBlogCategory(AddBlogCategoryRequestDto addBlogCategoryRequestDto) {
		Blog blog = blogRepository.findById(addBlogCategoryRequestDto.getBlogid()).orElse(null);
		Category category = categoryRepository.findById(addBlogCategoryRequestDto.getCategoryid()).orElse(null);
		if(blog==null || category==null) {
			return new ResponseEntity<>("Blog or Category not found by id!", HttpStatus.NO_CONTENT);
		}
		List<BlogCategory> blogCategories = blogCategoryRepository.findByBlog(blog);
		if(blogCategories.size()>=5) {
			return new ResponseEntity<>("Theres is a maximum categories for blog!", HttpStatus.NO_CONTENT);
		}
		if(checkBlogAndCategoryPairIsAvailable(blog, category)) {
			return new ResponseEntity<>("There is already available Blog and Category pair!", 
								 HttpStatus.NOT_ACCEPTABLE);
		}
		BlogCategory blogCategory = new BlogCategory();
		blogCategory.setBlog(blog);
		blogCategory.setCategory(category);
		blogCategoryRepository.save(blogCategory);
		return new ResponseEntity<>("Category is succesfully added to blog!", HttpStatus.OK);
	}
	
	public ResponseEntity<?> deleteBlogCategory(long blogid, long categoryid) {
		Blog blog = blogRepository.findById(blogid).orElse(null);
		Category category = categoryRepository.findById(categoryid).orElse(null);
		if(blog==null || category==null) {
			return new ResponseEntity<>("Blog or Category not found by id!", HttpStatus.NO_CONTENT);
		}
		List<BlogCategory> blogCategories = blogCategoryRepository.findByBlogAndCategory(blog, category);
		if(blogCategories!=null && blogCategories.size()>0) {
			blogCategoryRepository.delete(blogCategories.get(0));
			return new ResponseEntity<>("Blog and Category succesfully deleted!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Blog and Category not found by id!", HttpStatus.NO_CONTENT);
		}
	}
	
	private boolean checkBlogAndCategoryPairIsAvailable(Blog blog, Category category) {
		List<BlogCategory> blogCategories = blogCategoryRepository.findByBlogAndCategory(blog, category);
		if(blogCategories!=null && blogCategories.size()>0)
			return true;
		return false;
	}
	
}
