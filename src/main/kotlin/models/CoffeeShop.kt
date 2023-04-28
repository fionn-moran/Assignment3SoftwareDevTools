package models

import utils.Utilities

data class CoffeeShop(var shopID: Int = 0,
                      var shopName: String,
                      var shopLocation: String,
                      var shopDetails: String,
                      var dateAdded: String,
                      var isCoffeeShopClosed: Boolean,
                      var sales : MutableSet<CoffeeShopSales> = mutableSetOf())

{

    private var lastSaleID = 0
    private fun getSaleID() = lastSaleID++

    fun addSale(sale: CoffeeShopSales) : Boolean {
        sale.saleID = getSaleID()
        return sales.add(sale)
    }

    fun numberOfSales() = sales.size

    fun findSale(id: Int): CoffeeShopSales?{
        return sales.find{ sale -> sale.saleID == id }
    }
/*
    fun listSales() =
        if (sales.isEmpty())  "\tNO ITEMS ADDED"
        else  Utilities.formatSetString(sales)

 */
}