package com.mycompany.carrotMarket.chat.dto;

public class ChatDTO {
	private String sellerId;

	private String buyerId;

	private int productId;

	public ChatDTO() {

	}

	public ChatDTO(String sellerId, String buyerId, int productId) {
		this.sellerId = sellerId;
		this.buyerId = buyerId;
		this.productId = productId;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "ChatDTO [sellerId=" + sellerId + ", buyerId=" + buyerId + ", productId=" + productId + "]";
	}
}
