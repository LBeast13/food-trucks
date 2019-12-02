package com.foodtrucks.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FoodTruckNotFoundAdvice {

	@ResponseBody
	@ExceptionHandler(FoodTruckNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String foodTruckNotFoundHandler(FoodTruckNotFoundException ex) {
		return ex.getMessage();
	}
}