package com.foodtrucks.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

//import static org.springframework.hateoas.server.mvc.ControllerLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.foodtrucks.Exception.FoodTruckNotFoundException;
import com.foodtrucks.jpa.repository.FoodTruckRepository;
import com.foodtrucks.model.FoodTruck;
import com.foodtrucks.services.Services;

/**
 * Controller class containing all the possible API REST calls
 * 
 * @author Liam
 *
 */
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class FoodTruckController {

	private final FoodTruckRepository repository;
	private final FoodTruckEntityModelAssembler assembler;

	FoodTruckController(FoodTruckRepository repository, FoodTruckEntityModelAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;
	}

	/**
	 * GET ALL
	 * 
	 * @return all the foodtrucks in the repository
	 */
	@GetMapping("/foodtrucks")
	CollectionModel<EntityModel<FoodTruck>> all() {
		List<EntityModel<FoodTruck>> foodtrucks = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());

		return new CollectionModel<>(foodtrucks, linkTo(methodOn(FoodTruckController.class).all()).withSelfRel());
	}

	/**
	 * GET ALL WITHIN CIRCLE
	 * 
	 * @param lat
	 *            Ref latitude
	 * @param lon
	 *            Ref longitude
	 * @param dist
	 *            Radius for the circle (in km)
	 * @return all the foodtrucks within a circle of the given radius
	 */
	@GetMapping("/foodtrucks/withincircle")
	CollectionModel<EntityModel<FoodTruck>> allWithinCircle(@RequestParam double lat, @RequestParam double lon,
			double dist) {
		List<EntityModel<FoodTruck>> foodtrucks = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());

		foodtrucks = Services.getFoodTrucksWithinCircle(foodtrucks, lat, lon, dist);

		return new CollectionModel<>(foodtrucks, linkTo(methodOn(FoodTruckController.class).all()).withSelfRel());

	}

	/**
	 * POST
	 * 
	 * Post method to add a new foodtruck to the repository
	 * 
	 * @param newFoodTruck
	 *            The food truck to add
	 * @return the added Foodtruck
	 * @throws URISyntaxException
	 */
	@PostMapping("/foodtrucks")
	ResponseEntity<?> newFoodTruck(@RequestBody FoodTruck newFoodTruck) throws URISyntaxException {
		EntityModel<FoodTruck> resource = assembler.toModel(repository.save(newFoodTruck));

		return ResponseEntity.created(new URI(resource.getContent().getId().toString())).body(resource);
	}

	/**
	 * GET SINGLE
	 * 
	 * Get method to get a food truck using an id.
	 * 
	 * @param id
	 *            The id of the target food truck
	 * @return the food truck if we found it, rise a FoodTruckNotFoundException
	 *         otherwise
	 */
	@GetMapping("/foodtrucks/{id}")
	EntityModel<FoodTruck> one(@PathVariable Long id) {
		FoodTruck foodtruck = repository.findById(id).orElseThrow(() -> new FoodTruckNotFoundException(id));

		return assembler.toModel(foodtruck);
	}

	/**
	 * PUT
	 * 
	 * Put method to update an existing food truck with the given id
	 * 
	 * @param newFoodTruck
	 *            the food truck with the updated fields
	 * @param id
	 *            the id of the existing food truck
	 * @return the updated food truck
	 * @throws URISyntaxException
	 */
	@PutMapping("/foodtrucks/{id}")
	ResponseEntity<?> replaceEmployee(@RequestBody FoodTruck newFoodTruck, @PathVariable Long id)
			throws URISyntaxException {

		FoodTruck updatedFoodTruck = repository.findById(id).map(foodTruck -> {
			foodTruck.setName(newFoodTruck.getName());
			foodTruck.setLatitude(newFoodTruck.getLatitude());
			foodTruck.setLongitude(newFoodTruck.getLongitude());
			return repository.save(foodTruck);
		}).orElseGet(() -> {
			newFoodTruck.setId(id);
			return repository.save(newFoodTruck);
		});

		EntityModel<FoodTruck> resource = assembler.toModel(updatedFoodTruck);

		return ResponseEntity.created(new URI(resource.getContent().getId().toString())).body(resource);
	}

	/**
	 * DELETE
	 * 
	 * Delete the food truck with the given ID
	 * 
	 * @param id
	 *            The foodtruck id to delete
	 */
	@DeleteMapping("/foodtrucks/{id}")
	ResponseEntity<?> deleteFoodTruck(@PathVariable Long id) {
		repository.deleteById(id);

		return ResponseEntity.noContent().build();
	}

}