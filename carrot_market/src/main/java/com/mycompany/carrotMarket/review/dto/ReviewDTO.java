package com.mycompany.carrotMarket.review.dto;

public class ReviewDTO {

	private int tradeId;

	private int productId;

	private String reviewerId;

	private int review;

	public ReviewDTO() {

	}

	public ReviewDTO(int productId, String reviewerId) {
		this.productId = productId;
		this.reviewerId = reviewerId;
	}

	public ReviewDTO(int tradeId, int productId, String reviewerId, int review) {
		this.tradeId = tradeId;
		this.productId = productId;
		this.reviewerId = reviewerId;
		this.review = review;
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

}
