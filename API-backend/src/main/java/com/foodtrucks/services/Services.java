package com.foodtrucks.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.EntityModel;

import com.foodtrucks.model.FoodTruck;

public class Services {

	public Services() {
	}

	/**
	 * Return the foodtrucks located within a circle of a specified radius from
	 * the given geo point
	 * 
	 * @param foodtrucks
	 *            All the foodtrucks
	 * @param lat
	 *            The latitude reference
	 * @param lon
	 *            The longitude reference
	 * @param distance
	 *            The maximum distance (circle radius)
	 * 
	 * @return All the foodtrucks within this circle zone
	 */
	public static List<EntityModel<FoodTruck>> getFoodTrucksWithinCircle(List<EntityModel<FoodTruck>> foodtrucks,
			double lat, double lon, double distance) {

		List<EntityModel<FoodTruck>> nearFoodtrucks = new ArrayList<EntityModel<FoodTruck>>();

		for (EntityModel<FoodTruck> foodtruck : foodtrucks) {

			FoodTruck ft = (FoodTruck) foodtruck.getContent();
			if (checkDistance(lat, lon, ft.getLatitude(), ft.getLongitude(), distance) == true) {
				nearFoodtrucks.add(foodtruck);
			}
		}

		return nearFoodtrucks;
	}

	/**
	 * 
	 * @param lat1
	 *            Latitude of the first point
	 * @param long1
	 *            Longitude of the first point
	 * @param lat2
	 *            Latitude of the second point
	 * @param long2
	 *            Longitude of the second point
	 * @param distance
	 *            the max distance
	 * 
	 * @return True if the distance between points is inferior or equal to the
	 *         max distance
	 */
	private static boolean checkDistance(double lat1, double lon1, double lat2, double lon2, double distance) {
		return (computeDistance(lat1, lon1, lat2, lon2) <= distance) ? true : false;
	}

	/**
	 * Compute the distance between two latitudes and longitudes using the
	 * haversine formula
	 * 
	 * @param lat1
	 *            Latitude of point 1
	 * @param lon1
	 *            Longitude of point 1
	 * @param lat2
	 *            Latitude of point 2
	 * @param lon2
	 *            Longitude of point 2
	 * @return The distance in km between the two points
	 */
	private static double computeDistance(double lat1, double lon1, double lat2, double lon2) {
		int R = 6371; // Radius of the earth in km
		double dLat = degToRad(lat2 - lat1); // deg2rad below
		double dLon = degToRad(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = R * c; // Distance in km
		return d;
	}

	/**
	 * Convert angle in degree to radian
	 * 
	 * @param deg
	 *            angle in degree
	 * @return angle in radian
	 */
	private static double degToRad(double deg) {
		return deg * (Math.PI / 180);
	}
}
