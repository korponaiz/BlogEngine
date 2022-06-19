package dev.zolee.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String categoryName;

	@OneToMany(mappedBy = "category")
	private List<Label> labels;
	
	@OneToMany(mappedBy = "category")
	private List<BlogCategory> blogCategories;
	
	public Category() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Label> getLabels() {
		return labels;
	}

	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public List<BlogCategory> getBlogCategories() {
		return blogCategories;
	}

	public void setBlogCategories(List<BlogCategory> blogCategories) {
		this.blogCategories = blogCategories;
	}

}
