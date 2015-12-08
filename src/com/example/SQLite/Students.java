package com.example.SQLite;

public class Students {

	private String name;
	private int age;
	private double score;
	private String isMarray;
	
	public Students(String name,int age,double score,String isMarray){
		this.name = name;
		this.age = age;
		this.score = score;
		this.isMarray = isMarray;
	}

	public Students() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getIsMarray() {
		return isMarray;
	}

	public void setIsMarray(String isMarray) {
		this.isMarray = isMarray;
	}

	
	
	
}
