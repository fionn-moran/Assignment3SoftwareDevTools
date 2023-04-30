# Coffee Shop App

This is a console-based application built using Kotlin in Intellij. The purpose of this app is to be a coffee shop management system.

## App Features:

### Models:

This app has two models - CoffeeShop, and CoffeeShopSales. These models are linked on a 0 -> Many relationship. Where a coffee shop can have many sales.

### Menu:

#### Coffee Shops:
- Menu Items for Adding, Listing, Updating, and Deleting a Coffee Shop.
  - Listing submenu to allow listing of All/Closed Coffee Shops.
- Menu Items for labelling Coffee Shops - Closed.
- Menu Items for Searching amongst Coffee Shops, and Counting Coffee Shops based on specific parameters.
- Menu Items for Persistence - Load/Save Coffee Shops, and an Exit option.

#### Sales:
- Menu Items for Adding, Updating, and Deleting Sales.
  - Listing submenu to allow listing of Fulfilled Sales.
- Menu Items for labelling Sales - Fulfilled.
- Menu Items for Searching amongst Sales - by Sale Details, and by exact price / minimum price.

### Logging:
- Logging capabilities are added via MicroUtils Kotlin-Logging.

### Utilities:
- ScannerInput utility is included for robust user I/0.

### Persistence:
- Functionality to read/write to both JSON and XML. 

### Gradle plugins:
- Dokka
- Linting
- jar

### jUnit testing:
- Testing for CRUD functionality of CoffeeShops.
- Testing for Search and Count methods.
