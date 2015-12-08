package com.example.listview;

import android.R.string;

public class Fruit {
	private String fruitNameString;
	private int fruitImageViewID;
	
	public Fruit(String fruitNameString,int fruitImageViewID) {
		// TODO Auto-generated constructor stub
		this.fruitImageViewID = fruitImageViewID;
		this.fruitNameString = fruitNameString;
	}

	public String getFruitNameString() {
		return fruitNameString;
	}

	public void setFruitNameString(String fruitNameString) {
		this.fruitNameString = fruitNameString;
	}

	public int getFruitImageViewID() {
		return fruitImageViewID;
	}

	public void setFruitImageViewID(int fruitImageViewID) {
		this.fruitImageViewID = fruitImageViewID;
	}
	
	
	
}
