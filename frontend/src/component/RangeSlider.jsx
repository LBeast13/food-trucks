import React, { Component } from 'react';
import '../App.css';

/**
 * Simple Range Slider Component
 */
class RangeSlider extends Component {

    constructor(props) {
        super(props);
    
        this.state = {
            value: props.default
        }
        
        this.handleChange = this.handleChange.bind(this);
    }

    /** On Slider Value Change */
    handleChange(e){
        this.setState({
          value: e.target.value
        });
        this.props.callbackFromParent(e.target.value);
    }

    render() {
        return (
            <>
                <label htmlFor="customRange">Choose the maximum search distance</label>
                <h2>{this.state.value} meters</h2>
                <input type="range" 
                       className="custom-range" 
                       id="customRange" 
                       min={this.props.min} 
                       max={this.props.max}
                       step={this.props.step}
                       defaultValue={this.state.value}
                       onChange={this.handleChange}/>
            </>
        )
    }
}

export default RangeSlider