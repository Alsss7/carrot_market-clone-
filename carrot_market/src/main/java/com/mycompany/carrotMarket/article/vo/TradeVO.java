package com.mycompany.carrotMarket.article.vo;

import java.util.Date;

public class TradeVO {
	private int tradeId;

	private int productId;

	private String buyerId;

	private Date tradedAt;

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

	public Date getTradedAt() {
		return tradedAt;
	}

	public void setTradedAt(Date tradedAt) {
		this.tradedAt = tradedAt;
	}

}
