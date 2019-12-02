package com.foodtrucks.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.opencsv.bean.CsvBindByName;

@Entity
public class FoodTruck {

	private @Id @GeneratedValue Long id;

	@CsvBindByName(column = "Applicant", required = true)
	private String name;

	@CsvBindByName(column = "Latitude", required = true)
	private double latitude;

	@CsvBindByName(column = "Longitude", required = true)
	private double longitude;

	public FoodTruck() {
	}

	public FoodTruck(String name, double latitude, double longitude) {
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	// --- GETTERS AND SETTERS

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
