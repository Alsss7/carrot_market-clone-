package com.mycompany.carrotMarket.article.dto;

public class UpdateStatusDTO {
	private int productId;
	
	private String status;

	public UpdateStatusDTO() {

	}

	public UpdateStatusDTO(int productId, String status) {
		super();
		this.productId = productId;
		this.status = status;
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

}
