package com.mycompany.carrotMarket.article.dto;

import java.util.List;

public class UpdateImagesDTO {
	private int productId;

	private List<Integer> keepImages;

	public UpdateImagesDTO() {

	}

	public UpdateImagesDTO(int productId, List<Integer> keepImages) {
		this.productId = productId;
		this.keepImages = keepImages;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public List<Integer> getKeepImages() {
		return keepImages;
	}

	public void setKeepImages(List<Integer> keepImages) {
		this.keepImages = keepImages;
	}

}
