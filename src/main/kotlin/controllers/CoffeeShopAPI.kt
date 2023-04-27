package controllers

import models.CoffeeShop
import utils.Utilities

class CoffeeShopAPI {

    private var coffeeShops = ArrayList<CoffeeShop>()

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


    // lists all coffee shops
    fun listCoffeeShops(): String =
        if  (coffeeShops.isEmpty()) "No coffee shops on the system"
        else formatListString(coffeeShops)

}


