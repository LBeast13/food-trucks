import axios from 'axios'

const FOODTRUCKS_API_URL = 'http://localhost:8080'

/**
 * Food Trucks API call Services
 */
class FoodTrucksDataService {

    retrieveAllFoodTrucks() {
        return axios.get(`${FOODTRUCKS_API_URL}/foodtrucks`);
    }

    retrieveFoodTrucksWithinCircle(lat,lon,dist){
        return axios.get(`${FOODTRUCKS_API_URL}/foodtrucks/withincircle/?lat=${lat}&lon=${lon}&dist=${dist}`);
    }
}

export default new FoodTrucksDataService()