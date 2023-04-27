package controllers

import models.CoffeeShop

class CoffeeShopAPI {

    private var coffeeShops = ArrayList<CoffeeShop>()

    // adds a new coffee shop to coffee shop array list
    fun add(coffeeShop: CoffeeShop): Boolean {
        return coffeeShops.add(coffeeShop)
    }
}