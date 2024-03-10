package com.mycompany.carrotMarket.article.dto;

public class SalesDTO {
	private String userId;

	private String status;

	public SalesDTO() {

	}

	public SalesDTO(String userId, String status) {
		super();
		this.userId = userId;
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
