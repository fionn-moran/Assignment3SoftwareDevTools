package models

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
}