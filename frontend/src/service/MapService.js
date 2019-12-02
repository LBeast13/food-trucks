import axios from 'axios'

const API = "http://open.mapquestapi.com/geocoding/v1/address"
const KEY = "PbwJQlZWu8gAGGRJUpmMGXwENg46PG6S"

/**
 * Services for the Geocoding API calls
 */
class MapService {

    retrieveCoordinates(address){
        address = this.urlify(address);
        return axios.get(`${API}?key=${KEY}&location=${address}`);
    }
    
    urlify(str){
        return str.split(' ').join('+');
    }
}

export default new MapService()