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

    fun listSales() =
        if (sales.isEmpty())  "\tNO ITEMS ADDED"
        else  Utilities.formatSetString(sales)

    fun updateSale(id: Int, newSale: CoffeeShopSales): Boolean {
        val foundSale = findSale(id)

        //if the sale exists, use the details passed in the newSale parameter to
        //update the found sale in the Set
        if (foundSale != null){
            foundSale.saleContents = newSale.saleContents
            foundSale.isSaleFulfilled = newSale.isSaleFulfilled
            return true
        }

        //if the sale was not found, return false, indicating that the update was not successful
        return false
    }

    fun deleteSale(id: Int): Boolean {
        return sales.removeIf { sale -> sale.saleID == id}
    }

}