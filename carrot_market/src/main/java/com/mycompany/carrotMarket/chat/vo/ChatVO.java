package com.mycompany.carrotMarket.chat.vo;

import java.util.Date;

public class ChatVO {
	private int chatId;

	private int productId;

	private String sellerId;

	private String buyerId;

	private Date createdAt;

	private Date lastSentAt;

	public ChatVO() {

	}

	public ChatVO(int productId, String sellerId, String buyerId) {
		this.productId = productId;
		this.sellerId = sellerId;
		this.buyerId = buyerId;
	}

	public int getChatId() {
		return chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastSentAt() {
		return lastSentAt;
	}

	public void setLastSentAt(Date lastSentAt) {
		this.lastSentAt = lastSentAt;
	}

	@Override
	public String toString() {
		return "ChatVO [productId=" + productId + ", sellerId=" + sellerId + ", buyerId=" + buyerId + "]";
	}

}
