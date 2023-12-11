package com.team5.beans;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="procedures")
public class Procedures {

	@Id
	@Column(name = "Code", nullable = false)
	private int code;

	@Column(name = "Cost", nullable = false)
	private double cost;

	@Column(name = "Name", nullable = false, length = 30)
	private String name;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public Procedures() {};
}
