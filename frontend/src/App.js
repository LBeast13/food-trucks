import React, { Component } from 'react';
import './App.css';
import FoodTrucksApp from './component/FoodTrucksApp';

/**
 * Base Component
 */
class App extends Component {
  render() {
    return (
      <div className="container-fluid App">
        <FoodTrucksApp />
      </div>
    );
  }
}

export default App;


