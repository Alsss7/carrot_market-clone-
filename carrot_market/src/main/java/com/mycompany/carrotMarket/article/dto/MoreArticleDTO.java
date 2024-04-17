package com.mycompany.carrotMarket.article.dto;

public class MoreArticleDTO {
	private String searchValue;

	private int articleListSize;

	private int nextArticleListSize;

	private String region;

	public MoreArticleDTO() {

	}

	public MoreArticleDTO(String searchValue, int articleListSize, int nextArticleListSize) {
		this.searchValue = searchValue;
		this.articleListSize = articleListSize;
		this.nextArticleListSize = nextArticleListSize;
	}

	public MoreArticleDTO(int articleListSize, int nextArticleListSize, String region) {
		this.articleListSize = articleListSize;
		this.nextArticleListSize = nextArticleListSize;
		this.region = region;
	}

	public MoreArticleDTO(String searchValue, int articleListSize, int nextArticleListSize, String region) {
		this.searchValue = searchValue;
		this.articleListSize = articleListSize;
		this.nextArticleListSize = nextArticleListSize;
		this.region = region;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public int getArticleListSize() {
		return articleListSize;
	}

	public void setArticleListSize(int articleListSize) {
		this.articleListSize = articleListSize;
	}

	public int getNextArticleListSize() {
		return nextArticleListSize;
	}

	public void setNextArticleListSize(int nextArticleListSize) {
		this.nextArticleListSize = nextArticleListSize;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
