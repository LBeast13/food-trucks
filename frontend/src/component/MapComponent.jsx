import React,{Component} from 'react';
import { Map, GoogleApiWrapper, Marker } from 'google-maps-react';
import FoodTruckDataService from '../service/FoodTruckDataService';
import MapService from '../service/MapService';

/**
 * The Google Map component with the search box
 */
export class MapComponent extends Component{

    constructor(props) {
        super(props);
    
        this.state = {
          mapcenter:{ lat: 37.7701375805015, lng: -122.406},
          foodtrucks: [],
          input: "",
          distance: this.props.distance
        }

        this.handleClick = this.handleClick.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    componentDidMount() {
      // Get user position and center the map on him
      navigator.geolocation.getCurrentPosition(
        pos => {
           this.setState({
            center: {
              lat: pos.coords.latitude,
              lng: pos.coords.longitude
            }
          });
        }
      )
    }

    /** Update the distance when parent distance state change */
    componentDidUpdate(prevProps) {
      if(prevProps.distance !== this.props.distance) {
        this.setState({distance: this.props.distance});
      }
    }

    /**
     * Search Food Trucks
     */
    handleClick() {
      
      // Retrieve latitude and longitude from the address
      MapService.retrieveCoordinates(this.state.input)
        .then(response => {
          const latitude = response.data.results[0].locations[0].latLng.lat;
          const longitude = response.data.results[0].locations[0].latLng.lng;

          this.setState({
            center: {lat:latitude,
                      lng:longitude}
          })

          // Retrieve the Food Trucks Within a Circle with the given radius
          const dist = this.state.distance/1000;
          FoodTruckDataService.retrieveFoodTrucksWithinCircle(latitude,longitude,dist)
            .then(resp => {
              console.log(resp);
              if(resp.data.hasOwnProperty("_embedded")){
                this.setState({
                  foodtrucks: resp.data._embedded.foodTruckList
                })
              } else {
                this.setState({
                  foodtrucks: []
                })
              }
            });
        });

    }

    /** Handle Change in the location input field */
    handleChange(e){
      this.setState({
        input: e.target.value
      });
    }

    /**
     * On mapcenter changed
     */
    _onMapChange = ({center, zoom}) => {
      this.setState({
        center: center,
        zoom: zoom,      
      });
    }

    /**
     * Display all the Markers on the map
     */
    displayMarkers = () => {
      return this.state.foodtrucks.map((foodtruck, index) => {
        return <Marker key={index} 
                       id={index} 
                       position={{lat: foodtruck.latitude,
                                  lng: foodtruck.longitude
                                }}
                       title={foodtruck.name}/>
      })
    }

    render(){
        return (<>
            <div className="row">
                <input className="form-control col-8"  
                      type="text"
                      placeholder="Enter a location..."
                      onChange={this.handleChange}/>
                <button className="btn btn-success col-4" 
                        type="button" 
                        onClick={this.handleClick}>
                    Find Food Trucks
                </button>
            </div>
            <div className="row">
            <Map
                className="col-12"
                google={this.props.google}
                onChange={this._onMapChange}
                center={this.state.center}
                zoom={this.state.zoom}
                style={mapStyles}
                >
                {this.displayMarkers()}
            </Map>
            </div>
            </>         
        );
    }
}

const mapStyles = {
    width: '100%',
    height: '100%',
  };

export default GoogleApiWrapper({
})(MapComponent);