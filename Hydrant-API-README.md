# API Endpont for finding length of firehoses required to extinguish a fire #

##Task we are solving !!

Lots of fire hydrants cover the entire area of New York City. In case of a fire, a fire brigade arrives with N trucks
and connects firehoses to the N nearest hydrants in the fire area. This repository calculate
the total length of firehoses required to extinguish a fire.

****

## Points to be Noted ##

1. The length of firehoses in response is a floating type, but it can be converted into number type.
2. The default radius for the hydrants look up is 500 meters and another extension by 250 meters if not found within the default limit.  **[configurable property - application.yml]** 
3. In case of failure of SODA API, 2 subsequent retry will be made with delay of 100ms **[configurable property - application.yml]**
4. Source Dataset URL [Nearest Hydrant List]("https://data.cityofnewyork.us/resource/5bgh-vtsn.json")


****

## Integration Test & Unit Test ##

Please refer src/integration (properties externalized resources/application-test.properties) and test/java for the various test scenarios. 

****

## Swagger Link

Hydrant API can also be accessible from the below swagger URL **(Note: Run the App.java to bring up the SpringBoot container up and running on 8080 port)**

http://localhost:8080/swagger-ui.html

**(Hydrant GET API detail)**

**{hostUrl}/hydrant/trucks/{totalTrucks}/points/latitude/{latitudeCoordinates}/longitude/{longitudeCoordinates}/firehose/length**

http://localhost:8080/hydrant/trucks/3/points/latitude/40.75377740880684/longitude/-73.72485402779134/firehose/length
