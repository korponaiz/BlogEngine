package dev.zolee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.zolee.entity.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

}
