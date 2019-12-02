import React, { Component } from 'react';
import MapComponent from './MapComponent';
import RangeSlider from './RangeSlider';

/**
 * Food Truck App parent Component
 */
class FoodTrucksApp extends Component {

    constructor(props) {
        super(props);

        this.state = {
            distance: 500   // The max search distance
        };
    }

    /**
     * Callback for the child slider when distance change
     */
    distanceCallback = (dist) => {
        this.setState({
            distance:dist
        });
        this.forceUpdate();
    }
      

    render() {
        return (
            <div>
                <h1>Food Trucks Application</h1>
                <RangeSlider callbackFromParent={this.distanceCallback} 
                             default="500" 
                             max="5000" 
                             min="0" 
                             step="100"/>
                <MapComponent distance={this.state.distance}/>
            </div>
        )
    }
}

export default FoodTrucksApp
