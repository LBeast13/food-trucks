package com.foodtrucks.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.foodtrucks.model.FoodTruck;

@Component
public class FoodTruckEntityModelAssembler implements RepresentationModelAssembler<FoodTruck, EntityModel<FoodTruck>> {

	/**
	 * Wrap the foodtruck in a EntityModel and add self link and general link.
	 */
	@Override
	public EntityModel<FoodTruck> toModel(FoodTruck foodtruck) {

		return new EntityModel<>(foodtruck,
				linkTo(methodOn(FoodTruckController.class).one(foodtruck.getId())).withSelfRel(),
				linkTo(methodOn(FoodTruckController.class).all()).withRel("foodtrucks"));
	}

}
