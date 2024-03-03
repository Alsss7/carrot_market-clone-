package com.mycompany.carrotMarket.article.vo;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class ArticleVO {
	private int productId;

	private String userId;

	private String title;

	private String description;

	private String sellOrShare;

	private int price;

	private String category;

	private String status;

	private String region;

	private String place;

	private List<MultipartFile> files;

	private List<String> filesName;

	private int likeCount;

	private int viewCount;

	private int chatCount;

	private Date createdAt;

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSellOrShare() {
		return sellOrShare;
	}

	public void setSellOrShare(String sellOrShare) {
		this.sellOrShare = sellOrShare;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public List<String> getFilesName() {
		return filesName;
	}

	public void setFilesName(List<String> filesName) {
		this.filesName = filesName;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getChatCount() {
		return chatCount;
	}

	public void setChatCount(int chatCount) {
		this.chatCount = chatCount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "번호 : " + getProductId() + " 회원 아이디 : " + getUserId();
	}

}