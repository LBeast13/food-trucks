# Food Trucks


This is a Web Application that tells the user what food trucks might be found near a specific location on a map.</br>
Dataset used available on [DataSF: Food Trucks](https://data.sfgov.org/Economy-and-Community/Mobile-Food-Facility-Permit/rqzj-sfat)

# Description of my solution
## Backend API REST

The technologies I used to build the API are :
- Spring Boot Framework (Java)
- H2 for in-memory database
- OpenCSV

I have chose to download the data from [DataSF: Food Trucks](https://data.sfgov.org/Economy-and-Community/Mobile-Food-Facility-Permit/rqzj-sfat) as a CSV. Then I simply extract the data of this file using OpenCSV and put it in the H2 in-memory database. That means that the data is not persisted after runtime, I make this choice because of the time constraint but this could be an upgrade in a next version.

Here are all the request you can make to the API :

### Create/Add a Food Truck 
```
POST /foodtrucks
Accept: application/json
Content-Type: application/json

{
"name" : "The Food Place",
"latitude" : "47.87",
"longitude" : "-121.785"
}

```

### Update a Food Truck 
```
PUT /foodtrucks/{Foodtruck Id}
Accept: application/json
Content-Type: application/json

{
"name" : "The Food Place",
"latitude" : "47.87",
"longitude" : "-121.785"
}

```

### Retrieve a list of all the Food Trucks
```
Get /foodtrucks
Accept: application/json
Content-Type: application/json

```

### Find a Food Truck
```
Get /foodtrucks/{FoodTruck Id}
Accept: application/json
Content-Type: application/json
```

### Delete a Todo Note Resource
```
DELETE /foodtrucks/{FoodTruck Id}
Accept: application/json
Content-Type: application/json
```

### Get all the Food Trucks within a circle with a specific radius from a given geographical point
```
Get /foodtrucks/withincircle?lat={latitude}&lon={longitude}&dist={circle radius}
Accept: application/json
Content-Type: application/json
```

## Frontend

The technologies I used to build the Frontend are :
- React JS Framework
- HTML/CSS
- Bootstrap
- MapQuest API
- Google Maps API

React is very powerful for the frontend. You can factorize a lot of code and structure it better.
I have created some components to make the code more maintainable. I have also created two Services :
- FoodTruckDataService to make the request to my API backend
- MapService to request the MapQuest API 

I use Google Maps API only to display the map because it has limited functionality for free users, that is why I used MapQuest API for other thing like converting an address into a geographic point (latitude and longitude).


# Getting started
## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

Once you have cloned the repository follow these simple steps.

## Setting up the Backend

First you must build the project. Open a terminal in the API-backend folder and enter the command :

`mvn clean install`

Once successfully built, run the service by using the following command :

`mvn spring-boot:run`

Then the API will be running on localhost:8080.

## Setting up the Frontend

Open a terminal in the frontend folder and install the nodes modules with the command :

`npm install`

After all the packages are installed you can finally start the frontend server with the command :

`npm start`

You can open a web browser and enjoy the app by entering the address [http://localhost:3000](http://localhost:3000)

# What's next ?

If I was to spend additional time on the project, I would have :
- Implemented some unit and integrity tests
- Set up a persistance system with a database (probably a NoSQL database to have a better scalability)
- Added some other features as displaying the food trucks detail (with their menu ?)
