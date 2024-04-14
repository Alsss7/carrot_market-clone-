package com.mycompany.carrotMarket.article.dto;

public class SearchDTO {
	private String value;

	private String region;

	public SearchDTO() {

	}

	public SearchDTO(String value, String region) {
		this.value = value;
		this.region = region;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

}
