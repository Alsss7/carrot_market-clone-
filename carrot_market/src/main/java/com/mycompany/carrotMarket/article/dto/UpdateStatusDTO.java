package com.mycompany.carrotMarket.article.dto;

public class UpdateStatusDTO {
	private int productId;

	private String status;

	private String buyerId;

	public UpdateStatusDTO() {

	}

	public UpdateStatusDTO(int productId, String status, String buyerId) {
		this.productId = productId;
		this.status = status;
		this.buyerId = buyerId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

}
