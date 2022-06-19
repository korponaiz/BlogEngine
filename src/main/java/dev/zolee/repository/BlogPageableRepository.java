package dev.zolee.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import dev.zolee.entity.Blog;

@Repository
public interface BlogPageableRepository extends PagingAndSortingRepository<Blog, Long> {

	Page<Blog> findAll(Pageable pageable);
	List<Blog> findAllById(long id, Pageable pageable);
	Page<Blog> findByBlogNameContaining(String blogName, Pageable pageable);
	Page<Blog> findByIdIn(List<Long> id, Pageable pageable);
	
}
