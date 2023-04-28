package models

data class CoffeeShopSales (var saleID: Int = 0,
                            var saleDescription : String,
                            var saleDate : String,
                            var saleContents: String,
                            var isSaleFulfilled: Boolean = false)