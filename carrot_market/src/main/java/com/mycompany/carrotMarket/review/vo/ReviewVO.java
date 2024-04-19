package com.mycompany.carrotMarket.review.vo;

import java.util.Date;

public class ReviewVO {
	private int reviewId;

	private int tradeId;

	private int productId;

	private String reviewerId;

	private int review;

	private Date createdAt;

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public int getTradeId() {
		return tradeId;
	}

	public void setTradeId(int tradeId) {
		this.tradeId = tradeId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getReviewerId() {
		return reviewerId;
	}

	public void setReviewerId(String reviewerId) {
		this.reviewerId = reviewerId;
	}

	public int getReview() {
		return review;
	}

	public void setReview(int review) {
		this.review = review;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "ReviewVO [reviewId=" + reviewId + ", tradeId=" + tradeId + ", productId=" + productId + ", reviewerId="
				+ reviewerId + ", review=" + review + ", createdAt=" + createdAt + "]";
	}
}
