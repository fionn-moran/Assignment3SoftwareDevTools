package utils

import models.CoffeeShopSales

object Utilities {

    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    @JvmStatic
    fun formatSetString(salesToFormat: Set<CoffeeShopSales>): String =
        salesToFormat
            .joinToString(separator = "\n") { sale -> "\t$sale" }
}
