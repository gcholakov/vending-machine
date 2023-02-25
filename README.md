# vending-machine

## How to run the application
In root folder, run next commands:

* mvn clean compile
* mvn test
* mvn javadoc:javadoc
* mvn spring-boot:run

The application will run on port 8082 to avoid conflicts.
If you need to change it, edit src\main\resources\application.properties file.

## API documentation
* [API documentation](http://localhost:8082/swagger-ui/index.html#/)
* JavaDoc is generated in \target\site\apidocs folder ([index.html](target%2Fsite%2Fapidocs%2Findex.html) or IntelliJ's [JavaDoc documentation](http://localhost:63342/vending-machine/target/site/apidocs/index.html))

## How to test the API
Integration tests are present for the controllers - InventoryControllerTest and VendingControllerTest,
covering all endpoints.

## Description
The persistence is simulated by Inventory and Vending classes in com.noser.vending.datastore package.
They could be replaced by H2 or similar, but this would make the solution more complex.

Application starts with four preloaded products to facilitate the usage and testing.
This is done in Inventory's @PostConstruct method.

All coins nominals and prices are in stotinki to avoid unnecessary calculations.

Buying a product is simplified and the algorithm is next: for provided product name and type (DRINK|FOOD) 
the list with products is searched for the first product matching those criteria, and whose price is <= amount of inserted coins so far.  

## Example of buying a product
* Let's insert some coins by calling post to localhost:8082/vending/insert with body:
  >{
  >"value": 50
  >}

Call it several times with different nominal to make sure we have enough amount.

* Execute post to localhost:8082/vending/buy with next body:
  >{
  >"name": "chips",
  >"productType": "FOOD"
  >}

This buy the chips if we have enough money, otherwise will return error with message.

For your convenience there are exported endpoint calls in file [vending-machine.postman_collection.json](vending-machine.postman_collection.json) for testing with Postman.

## Possible improvements:
* increase the number of test cases;
* add more validations.