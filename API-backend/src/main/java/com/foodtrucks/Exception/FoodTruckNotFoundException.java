package com.foodtrucks.Exception;

public class FoodTruckNotFoundException extends RuntimeException {

	public FoodTruckNotFoundException(Long id) {
		super("Could not find food truck " + id);
	}
}
