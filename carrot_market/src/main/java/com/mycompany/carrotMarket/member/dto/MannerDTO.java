package com.mycompany.carrotMarket.member.dto;

public class MannerDTO {
	private String id;

	private float amountOfIncrease;

	public MannerDTO(String id, float amountOfIncrease) {
		super();
		this.id = id;
		this.amountOfIncrease = amountOfIncrease;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getAmountOfIncrease() {
		return amountOfIncrease;
	}

	public void setAmountOfIncrease(float amountOfIncrease) {
		this.amountOfIncrease = amountOfIncrease;
	}
}
