package controllers

import models.CoffeeShop
import models.CoffeeShopSales
import utils.Utilities

class CoffeeShopAPI {

    private var coffeeShops = ArrayList<CoffeeShop>()
    private var sales = ArrayList<CoffeeShopSales>()

    // adds a new coffee shop to coffee shop array list
    fun add(coffeeShop: CoffeeShop): Boolean {
        return coffeeShops.add(coffeeShop)
    }

    // Retrieves a coffee shop based on a specific index
    fun findCoffeeShop(index: Int): CoffeeShop? {
        return if (Utilities.isValidListIndex(index, coffeeShops)) {
            coffeeShops[index]
        } else null
    }

    // updates a previously added note with additional/changed details
    fun updateCoffeeShop(indexToUpdate: Int, coffeeShop: CoffeeShop?): Boolean {
        //find the coffee shop by the index number
        val foundCoffeeShop = findCoffeeShop(indexToUpdate)

        //if the coffee shop exists, use the coffee shop details passed as parameters to update the found coffee shop in the ArrayList.
        if ((foundCoffeeShop != null) && (coffeeShop != null)) {
            foundCoffeeShop.shopName = coffeeShop.shopName
            foundCoffeeShop.shopLocation = coffeeShop.shopLocation
            foundCoffeeShop.shopDetails = coffeeShop.shopDetails
            foundCoffeeShop.dateAdded = coffeeShop.dateAdded
            return true
        }

        //if the coffee shop was not found, return false, indicating that the update was not successful
        return false
    }

    fun numberOfCoffeeShops() = coffeeShops.size

    // helper function formats list of coffee shops and returns them as string
    private fun formatListString(coffeeShopsToFormat : List<CoffeeShop>) : String =
        coffeeShopsToFormat
            .joinToString (separator = "\n") { coffeeShop ->
                coffeeShops.indexOf(coffeeShop).toString() + ": " + coffeeShop.toString()
            }

    private fun formatSalesListString(salesToFormat : List<CoffeeShopSales>) : String =
        salesToFormat
            .joinToString (separator = "\n") { sale ->
                sales.indexOf(sale).toString() + ": " + sale.toString()
            }
    // lists all coffee shops
    fun listAllCoffeeShops(): String =
        if  (coffeeShops.isEmpty()) "no coffee shops on the system"
        else formatListString(coffeeShops)

    fun listAllSales(): String =
        if  (sales.isEmpty()) "No coffee shops on the system"
        else formatSalesListString(sales)

    fun listClosedCoffeeShops(): String =
        if  (numberOfClosedCoffeeShops() == 0) "No archived notes stored"
        else formatListString(coffeeShops.filter { coffeeShop -> coffeeShop.isCoffeeShopClosed})

    fun removeCoffeeShop(indexToDelete: Int): CoffeeShop? {
        return if (Utilities.isValidListIndex(indexToDelete, coffeeShops)) {
            coffeeShops.removeAt(indexToDelete)
        } else null
    }


    fun closeCoffeeShop(id: Int): Boolean {
        val foundCoffeeShop = findCoffeeShop(id)
        if (( foundCoffeeShop != null) && (!foundCoffeeShop.isCoffeeShopClosed)
        ){
            foundCoffeeShop.isCoffeeShopClosed = true
            return true
        }
        return false
    }

    // Counts the total number of all archived notes
    fun numberOfClosedCoffeeShops(): Int = coffeeShops.count {
            coffeeShop: CoffeeShop -> coffeeShop.isCoffeeShopClosed
    }

    // searches for input text in shop names
    fun searchByCoffeeShopName (searchString : String) =
        formatListString(
            coffeeShops.filter { coffeeShop -> coffeeShop.shopName.contains(searchString, ignoreCase = true) })

    fun searchSaleByContents(searchString: String): String {
        return if (numberOfCoffeeShops() == 0) "No coffee shops stored"
        else {
            var listOfCoffeeShops = ""
            for (coffeeShop in coffeeShops) {
                for (sale in coffeeShop.sales) {
                    if (sale.saleContents.contains(searchString, ignoreCase = true)) {
                        listOfCoffeeShops += "${coffeeShop.shopID}: ${coffeeShop.shopName} \n\t${sale}\n"
                    }
                }
            }
            if (listOfCoffeeShops == "") "No items found for: $searchString"
            else listOfCoffeeShops
        }
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR SALES
    // ----------------------------------------------
    fun listFulfilledSales(): String =
        if (numberOfCoffeeShops() == 0) "No shops stored"
        else {
            var listOfFulfilledSales = ""
            for (coffeeShop in coffeeShops) {
                for (sale in coffeeShop.sales) {
                    if (!sale.isSaleFulfilled) {
                        listOfFulfilledSales += coffeeShop.shopName + ": " + sale.saleContents + "\n"
                    }
                }
            }
            listOfFulfilledSales
        }

    // ----------------------------------------------
    //  COUNTING METHODS FOR SALES
    // ----------------------------------------------
    fun numberOfFulfilledSales(): Int {
        var numberOfFulfilledSales = 0
        for (coffeeShop in coffeeShops) {
            for (sale in coffeeShop.sales) {
                if (!sale.isSaleFulfilled) {
                    numberOfFulfilledSales++
                }
            }
        }
        return numberOfFulfilledSales
    }
}


