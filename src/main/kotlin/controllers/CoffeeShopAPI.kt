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




}