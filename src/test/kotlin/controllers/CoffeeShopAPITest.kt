package controllers

import models.CoffeeShop
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class CoffeeShopAPITest {

    private var costa: CoffeeShop? = null
    private var starbucks: CoffeeShop? = null
    private var mcdonalds: CoffeeShop? = null
    private var populatedCoffeeShops: CoffeeShopAPI? = CoffeeShopAPI(XMLSerializer(File("notes.xml")))
    private var emptyCoffeeShops: CoffeeShopAPI? = CoffeeShopAPI(XMLSerializer(File("notes.xml")))

    @BeforeEach
    fun setup() {
        costa = CoffeeShop(1, "Costa", "Waterford", "Open", "1/1/2020", true)
        starbucks = CoffeeShop(2, "Starbucks", "Dublin", "Open", "10/2/2023", false)
        mcdonalds = CoffeeShop(3, "Mcdonalds", "Kilkenny", "Open", "8/9/2015", false)

        //adding 3 Shops to the CoffeeShops api
        populatedCoffeeShops!!.add(costa!!)
        populatedCoffeeShops!!.add(starbucks!!)
        populatedCoffeeShops!!.add(mcdonalds!!)
    }

    @AfterEach
    fun tearDown() {
        costa = null
        starbucks = null
        mcdonalds = null
        populatedCoffeeShops = null
        emptyCoffeeShops = null
    }

    @Nested
    inner class AddCoffeeShops {
        // Add to coffee shop tests
        @Test
        fun `adding a Coffee Shop to a populated list adds to ArrayList`() {
            val newCoffeeShop = CoffeeShop(1, "Coffee Shop 1", "College", "Open", "1/1/2000", false)
            assertEquals(3, populatedCoffeeShops!!.numberOfCoffeeShops())
            assertTrue(populatedCoffeeShops!!.add(newCoffeeShop))
            assertEquals(4, populatedCoffeeShops!!.numberOfCoffeeShops())
            assertEquals(
                newCoffeeShop,
                populatedCoffeeShops!!.findCoffeeShop(populatedCoffeeShops!!.numberOfCoffeeShops() - 1)
            )
        }

        @Test
        fun `adding a Coffee Shop to an empty list adds to ArrayList`() {
            val newCoffeeShop = CoffeeShop(2, "Coffee Shop 2", "Cork", "Open", "7/3/2019", false)
            assertEquals(0, emptyCoffeeShops!!.numberOfCoffeeShops())
            assertTrue(emptyCoffeeShops!!.add(newCoffeeShop))
            assertEquals(1, emptyCoffeeShops!!.numberOfCoffeeShops())
            assertEquals(newCoffeeShop, emptyCoffeeShops!!.findCoffeeShop(emptyCoffeeShops!!.numberOfCoffeeShops() - 1))
        }
    }

    @Nested
    inner class ListCoffeeShops {

        @Test
        fun `listAllCoffeeShops returns No Coffee Shops Stored message when ArrayList is empty`() {
            assertEquals(0, emptyCoffeeShops!!.numberOfCoffeeShops())
            assertTrue(emptyCoffeeShops!!.listAllCoffeeShops().lowercase().contains("no coffee shops on the system"))
        }

        @Test
        fun `listAllCoffeeShops returns shops when ArrayList has shops stored`() {
            assertEquals(3, populatedCoffeeShops!!.numberOfCoffeeShops())
            val coffeeShopsString = populatedCoffeeShops!!.listAllCoffeeShops().lowercase()
            assertTrue(coffeeShopsString.contains("costa"))
            assertTrue(coffeeShopsString.contains("starbucks"))
            assertTrue(coffeeShopsString.contains("mcdonalds"))
        }
    }

    @Nested
    inner class UpdateCoffeeShops {
        @Test
        fun `updating a shop that does not exist returns false`() {
            assertFalse(populatedCoffeeShops!!.updateCoffeeShop(6, CoffeeShop(9, "Coffee 4", "Limerick", "Coffee Shop 12", "1/1/2002", false)))
            assertFalse(populatedCoffeeShops!!.updateCoffeeShop(-1, CoffeeShop(20, "Coffee 5", "Limerick", "Coffee Shop 212", "12/1/2012", false)))
            assertFalse(populatedCoffeeShops!!.updateCoffeeShop(99, CoffeeShop(40, "Coffee 6", "Limerick", "Coffee Shop 675", "7/6/2019", false)))
        }

        @Test
        fun `updating a shop that exists returns true and updates`() {
            //check shop 3 exists and check the contents
            assertEquals(mcdonalds, populatedCoffeeShops!!.findCoffeeShop(2))
            assertEquals("Mcdonalds", populatedCoffeeShops!!.findCoffeeShop(2)!!.shopName)
            assertEquals("Kilkenny", populatedCoffeeShops!!.findCoffeeShop(2)!!.shopLocation)
            assertEquals("Open", populatedCoffeeShops!!.findCoffeeShop(2)!!.shopDetails)
            assertEquals("8/9/2015", populatedCoffeeShops!!.findCoffeeShop(2)!!.dateAdded)
            assertEquals(false, populatedCoffeeShops!!.findCoffeeShop(2)!!.isCoffeeShopClosed)

            //update shop 3 with new information and ensure contents updated successfully
            assertTrue(populatedCoffeeShops!!.updateCoffeeShop(2, CoffeeShop(2, "Coffee Shop 99", "Main Street", "Open 24/7", "1/11/2010", false)))
            assertEquals("Coffee Shop 99", populatedCoffeeShops!!.findCoffeeShop(2)!!.shopName)
            assertEquals("Main Street", populatedCoffeeShops!!.findCoffeeShop(2)!!.shopLocation)
            assertEquals("Open 24/7", populatedCoffeeShops!!.findCoffeeShop(2)!!.shopDetails)
            assertEquals("1/11/2010", populatedCoffeeShops!!.findCoffeeShop(2)!!.dateAdded)
            assertEquals(false, populatedCoffeeShops!!.findCoffeeShop(2)!!.isCoffeeShopClosed)
        }
    }


    @Nested
    inner class DeleteCoffeeShops {

        @Test
        fun `deleting a Coffee Shop that does not exist, returns null`() {
            assertNull(emptyCoffeeShops!!.removeCoffeeShop(100))
            assertNull(populatedCoffeeShops!!.removeCoffeeShop(-1))
            assertNull(populatedCoffeeShops!!.removeCoffeeShop(5))
        }

        @Test
        fun `deleting a shop that exists delete and returns deleted object`() {
            assertEquals(3, populatedCoffeeShops!!.numberOfCoffeeShops())
            assertEquals(costa, populatedCoffeeShops!!.removeCoffeeShop(0))
            assertEquals(2, populatedCoffeeShops!!.numberOfCoffeeShops())
            assertEquals(starbucks, populatedCoffeeShops!!.removeCoffeeShop(0))
            assertEquals(1, populatedCoffeeShops!!.numberOfCoffeeShops())
        }
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty coffeeShops.XML file.
            val storingCoffeeShops = CoffeeShopAPI(XMLSerializer(File("coffeeShops.xml")))
            storingCoffeeShops.store()

            //Loading the empty notes.xml file into a new object
            val loadedCoffeeShops = CoffeeShopAPI(XMLSerializer(File("coffeeShops.xml")))
            loadedCoffeeShops.load()

            //Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(0, storingCoffeeShops.numberOfCoffeeShops())
            assertEquals(0, loadedCoffeeShops.numberOfCoffeeShops())
            assertEquals(storingCoffeeShops.numberOfCoffeeShops(), loadedCoffeeShops.numberOfCoffeeShops())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't lose data`() {
            // Storing 3 shops to the notes.XML file.
            val storingCoffeeShops = CoffeeShopAPI(XMLSerializer(File("coffeeShops.xml")))
            storingCoffeeShops.add(costa!!)
            storingCoffeeShops.add(starbucks!!)
            storingCoffeeShops.add(mcdonalds!!)
            storingCoffeeShops.store()

            //Loading coffeeShops.xml into a different collection
            val loadedCoffeeShops = CoffeeShopAPI(XMLSerializer(File("coffeeShops.xml")))
            loadedCoffeeShops.load()

            //Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(3, storingCoffeeShops.numberOfCoffeeShops())
            assertEquals(3, loadedCoffeeShops.numberOfCoffeeShops())
            assertEquals(storingCoffeeShops.numberOfCoffeeShops(), loadedCoffeeShops.numberOfCoffeeShops())
            assertEquals(storingCoffeeShops.findCoffeeShop(0), loadedCoffeeShops.findCoffeeShop(0))
            assertEquals(storingCoffeeShops.findCoffeeShop(1), loadedCoffeeShops.findCoffeeShop(1))
            assertEquals(storingCoffeeShops.findCoffeeShop(2), loadedCoffeeShops.findCoffeeShop(2))
        }
    }
}