package dev.zolee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.zolee.entity.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

	List<Label> findByCategoryLabel(String categoryLabel);
	
}
