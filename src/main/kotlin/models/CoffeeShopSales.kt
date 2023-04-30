package models

data class CoffeeShopSales (var saleID: Int = 0,
                            var saleContents: String,
                            var salePrice: Double,
                            var isSaleFulfilled: Boolean = false)