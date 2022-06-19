package dev.zolee.dto;

import java.util.List;

public class CategoryRequestDto {

	private String categoryName;
	private List<String> categoryLabel;

	public CategoryRequestDto() {
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<String> getCategoryLabel() {
		return categoryLabel;
	}

	public void setCategoryLabel(List<String> categoryLabel) {
		this.categoryLabel = categoryLabel;
	}

}
