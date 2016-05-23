package com.cqecom.cms.dynamicpricing;

import java.util.Date;

public class PriceItem {
	private String testPrice;
	private String upsellPrice;
	private String price;
	private String level;
	private Date modifiedTime;

	public String getTestPrice() {
		return testPrice;
	}

	public void setTestPrice(String testPrice) {
		this.testPrice = testPrice;
	}

	public String getUpsellPrice() {
		return upsellPrice;
	}

	public void setUpsellPrice(String upsellPrice) {
		this.upsellPrice = upsellPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

}
