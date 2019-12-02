package com.foodtrucks.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.foodtrucks.model.FoodTruck;

public interface FoodTruckRepository extends JpaRepository<FoodTruck, Long> {

}
