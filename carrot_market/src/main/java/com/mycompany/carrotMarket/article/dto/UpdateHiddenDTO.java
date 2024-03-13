package com.mycompany.carrotMarket.article.dto;

public class UpdateHiddenDTO {
	private int productId;
	private int hidden;

	public UpdateHiddenDTO() {

	}

	public UpdateHiddenDTO(int productId, int hidden) {
		super();
		this.productId = productId;
		this.hidden = hidden;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getHidden() {
		return hidden;
	}

	public void setHidden(int hidden) {
		this.hidden = hidden;
	}

}
