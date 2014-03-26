package com.carl.jackson.imagesearch;

import java.io.Serializable;

public class SearchSettings implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3115107851899726431L;
	private String imageSize;
	private String colorFilter;
	private String imageType;
	private String siteFilter;

	public SearchSettings () {
		super();
		imageSize = "";
		colorFilter = "";
		imageType = "";
		siteFilter = "";
	}

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public String getColorFilter() {
		return colorFilter;
	}

	public void setColorFilter(String colorFilter) {
		this.colorFilter = colorFilter;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getSiteFilter() {
		return siteFilter;
	}

	public void setSiteFilter(String siteFilter) {
		this.siteFilter = siteFilter;
	}
}
