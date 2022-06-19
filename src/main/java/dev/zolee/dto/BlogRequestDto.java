package dev.zolee.dto;

public class BlogRequestDto {

	private String blogName;
	private String blogText;

	public BlogRequestDto() {
	}

	public String getBlogName() {
		return blogName;
	}

	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}

	public String getBlogText() {
		return blogText;
	}

	public void setBlogText(String blogText) {
		this.blogText = blogText;
	}

}
