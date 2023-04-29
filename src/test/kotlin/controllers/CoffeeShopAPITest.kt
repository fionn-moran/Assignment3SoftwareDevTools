package controllers

import models.CoffeeShop
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CoffeeShopAPITest {

    private var costa: CoffeeShop? = null
    private var starbucks: CoffeeShop? = null
    private var mcdonalds: CoffeeShop? = null
    private var populatedCoffeeShops: CoffeeShopAPI? = CoffeeShopAPI()
    private var emptyCoffeeShops: CoffeeShopAPI? = CoffeeShopAPI()

    @BeforeEach
    fun setup(){
        costa = CoffeeShop(1, "Costa", "Waterford", "Open", "1/1/2020", true)
        starbucks = CoffeeShop(2, "Starbucks", "Dublin", "Open", "10/2/2023", false)
        mcdonalds = CoffeeShop(3, "Mcdonalds", "Kilkenny", "Open", "8/9/2015", false)

        //adding 3 Shops to the CoffeeShops api
        populatedCoffeeShops!!.add(costa!!)
        populatedCoffeeShops!!.add(starbucks!!)
        populatedCoffeeShops!!.add(mcdonalds!!)
    }

    @AfterEach
    fun tearDown(){
        costa = null
        starbucks = null
        mcdonalds = null
        populatedCoffeeShops = null
        emptyCoffeeShops = null
    }

    @Test
    fun `adding a Coffee Shop to a populated list adds to ArrayList`(){
        val newCoffeeShop = CoffeeShop(1, "Coffee Shop 1", "College", "Open", "1/1/2000", false)
        assertTrue(populatedCoffeeShops!!.add(newCoffeeShop))
    }

    @Test
    fun `adding a Coffee Shop to an empty list adds to ArrayList`(){
        val newCoffeeShop = CoffeeShop(2, "Coffee Shop 2", "Cork", "Open", "7/3/2019", false)
        assertTrue(emptyCoffeeShops!!.add(newCoffeeShop))
    }
}