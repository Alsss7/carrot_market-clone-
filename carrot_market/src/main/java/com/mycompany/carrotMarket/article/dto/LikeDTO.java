package com.mycompany.carrotMarket.article.dto;

import java.util.Date;

public class LikeDTO {
	private String userId;

	private int productId;

	private Date createdAt;

	public LikeDTO() {

	}

	public LikeDTO(String userId, int productId) {
		this.userId = userId;
		this.productId = productId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
