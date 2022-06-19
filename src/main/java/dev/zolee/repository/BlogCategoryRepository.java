package dev.zolee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.zolee.entity.Blog;
import dev.zolee.entity.BlogCategory;
import dev.zolee.entity.Category;

@Repository
public interface BlogCategoryRepository extends JpaRepository<BlogCategory, Long> {
	
	List<BlogCategory> findByBlog(Blog blog);
	
	List<BlogCategory> findByCategory(Category category);
	
	List<BlogCategory> findByBlogAndCategory(Blog blog, Category category);
	
}
