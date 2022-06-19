package dev.zolee.dto;

public class AddBlogCategoryRequestDto {

	private long blogid;
	private long categoryid;

	public AddBlogCategoryRequestDto() {
	}

	public long getBlogid() {
		return blogid;
	}

	public void setBlogid(long blogid) {
		this.blogid = blogid;
	}

	public long getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(long categoryid) {
		this.categoryid = categoryid;
	}

}
