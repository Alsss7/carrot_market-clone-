package com.mycompany.carrotMarket.trade.dto;

public class TradeDTO {

	public TradeDTO(int productId, String buyerId) {
		this.productId = productId;
		this.buyerId = buyerId;
	}

	private int tradeId;

	private int productId;

	private String buyerId;

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

	public String getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(String buyerId) {
		this.buyerId = buyerId;
	}

}
