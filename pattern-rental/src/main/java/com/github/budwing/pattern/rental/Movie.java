package com.github.budwing.pattern.rental;

public class Movie {
	public static final int CHILDREN = 2;
	public static final int REGULAR = 0;
	public static final int NEW_RELEASE = 1;
	
	private String title;
	private int priceCode;
	
	public Movie(String title, int priceCode) {
		super();
		this.title = title;
		this.priceCode = priceCode;
	}

	public int getPriceCode() {
		return priceCode;
	}

	public void setPriceCode(int priceCode) {
		this.priceCode = priceCode;
	}

	public String getTitle() {
		return title;
	}
}
