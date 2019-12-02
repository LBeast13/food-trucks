package com.foodtrucks;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.foodtrucks.jpa.repository.FoodTruckRepository;
import com.foodtrucks.model.FoodTruck;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

/**
 * Loading data class
 * 
 * @author Liam
 *
 */
@Configuration
@Slf4j
public class LoadDatabase {

	private static final String DATA_CSV_FILE_PATH = ".\\src\\main\\resources\\static\\foodtruck_data.csv";

	/**
	 * Load the data from the CSV file to the FoodTruck repository The binding
	 * for the columns is directly set in the FoodTruck model
	 * 
	 * @param repository
	 *            The food truck repository
	 */
	@Bean
	CommandLineRunner initDatabase(FoodTruckRepository repository) throws IOException {

		try (Reader reader = Files.newBufferedReader(Paths.get(DATA_CSV_FILE_PATH));) {

			CsvToBean<FoodTruck> csvToBean = new CsvToBeanBuilder(reader).withType(FoodTruck.class)
					.withIgnoreLeadingWhiteSpace(true).build();

			Iterator<FoodTruck> foodtruckIterator = csvToBean.iterator();
			Logger log = LoggerFactory.getLogger(LoadDatabase.class);

			log.info("Preloading Foodtrucks...");

			while (foodtruckIterator.hasNext()) {
				FoodTruck foodtruck = foodtruckIterator.next();
				repository.save(foodtruck);
			}
			return args -> {
				log.info("Food trucks successfully loaded");
			};
		}
	}
}
