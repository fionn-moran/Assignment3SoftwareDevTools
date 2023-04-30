package controllers

import models.CoffeeShop
import models.CoffeeShopSales
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class CoffeeShopAPITest {

    private var costa: CoffeeShop? = null
    private var starbucks: CoffeeShop? = null
    private var mcdonalds: CoffeeShop? = null
    private var populatedCoffeeShops: CoffeeShopAPI? = CoffeeShopAPI(XMLSerializer(File("notes.xml")))
    private var emptyCoffeeShops: CoffeeShopAPI? = CoffeeShopAPI(XMLSerializer(File("notes.xml")))

    private var americano: CoffeeShopSales? = null
    private var latte: CoffeeShopSales? = null
    private var mocha: CoffeeShopSales? = null

    @BeforeEach
    fun setup() {
        costa = CoffeeShop(1, "Costa", "Waterford", "Open", LocalDate.of(2002, 1, 1), true)
        starbucks = CoffeeShop(2, "Starbucks", "Dublin", "Open", LocalDate.of(2023, 2, 10), false)
        mcdonalds = CoffeeShop(3, "Mcdonalds", "Kilkenny", "Open", LocalDate.of(2020, 5, 5), false)

        americano = CoffeeShopSales(4, "Sale", 2.50, false)
        latte = CoffeeShopSales(5, "Sell 1", 3.10, true)
        mocha = CoffeeShopSales(6, "Sold 2", 3.60, false)

        // adding 3 Shops to the CoffeeShops api
        populatedCoffeeShops!!.add(costa!!)
        populatedCoffeeShops!!.add(starbucks!!)
        populatedCoffeeShops!!.add(mcdonalds!!)

        costa!!.addSale(americano!!)
        starbucks!!.addSale(latte!!)
        mcdonalds!!.addSale(mocha!!)
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
            val newCoffeeShop = CoffeeShop(1, "Coffee Shop 1", "College", "Open", LocalDate.of(2009, 4, 9), false)
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
            val newCoffeeShop = CoffeeShop(2, "Coffee Shop 2", "Cork", "Open", LocalDate.of(2010, 7, 8), false)
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
            assertFalse(populatedCoffeeShops!!.updateCoffeeShop(6, CoffeeShop(9, "Coffee 4", "Limerick", "Coffee Shop 12", LocalDate.of(2012, 2, 19), false)))
            assertFalse(populatedCoffeeShops!!.updateCoffeeShop(-1, CoffeeShop(20, "Coffee 5", "Limerick", "Coffee Shop 212", LocalDate.of(2017, 4, 13), false)))
            assertFalse(populatedCoffeeShops!!.updateCoffeeShop(99, CoffeeShop(40, "Coffee 6", "Limerick", "Coffee Shop 675", LocalDate.of(2016, 3, 25), false)))
        }

        @Test
        fun `updating a shop that exists returns true and updates`() {
            // check shop 3 exists and check the contents
            assertEquals(mcdonalds, populatedCoffeeShops!!.findCoffeeShop(2))
            assertEquals("Mcdonalds", populatedCoffeeShops!!.findCoffeeShop(2)!!.shopName)
            assertEquals("Kilkenny", populatedCoffeeShops!!.findCoffeeShop(2)!!.shopLocation)
            assertEquals("Open", populatedCoffeeShops!!.findCoffeeShop(2)!!.shopDetails)
            assertEquals(LocalDate.of(2020, 5, 5), populatedCoffeeShops!!.findCoffeeShop(2)!!.dateAdded)
            assertEquals(false, populatedCoffeeShops!!.findCoffeeShop(2)!!.isCoffeeShopClosed)

            // update shop 3 with new information and ensure contents updated successfully
            assertTrue(populatedCoffeeShops!!.updateCoffeeShop(2, CoffeeShop(2, "Coffee Shop 99", "Main Street", "Open 24/7", LocalDate.of(2022, 1, 1), false)))
            assertEquals("Coffee Shop 99", populatedCoffeeShops!!.findCoffeeShop(2)!!.shopName)
            assertEquals("Main Street", populatedCoffeeShops!!.findCoffeeShop(2)!!.shopLocation)
            assertEquals("Open 24/7", populatedCoffeeShops!!.findCoffeeShop(2)!!.shopDetails)
            assertEquals(LocalDate.of(2022, 1, 1), populatedCoffeeShops!!.findCoffeeShop(2)!!.dateAdded)
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

            // Loading the empty notes.xml file into a new object
            val loadedCoffeeShops = CoffeeShopAPI(XMLSerializer(File("coffeeShops.xml")))
            loadedCoffeeShops.load()

            // Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
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

            // Loading coffeeShops.xml into a different collection
            val loadedCoffeeShops = CoffeeShopAPI(XMLSerializer(File("coffeeShops.xml")))
            loadedCoffeeShops.load()

            // Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(3, storingCoffeeShops.numberOfCoffeeShops())
            assertEquals(3, loadedCoffeeShops.numberOfCoffeeShops())
            assertEquals(storingCoffeeShops.numberOfCoffeeShops(), loadedCoffeeShops.numberOfCoffeeShops())
            assertEquals(storingCoffeeShops.findCoffeeShop(0), loadedCoffeeShops.findCoffeeShop(0))
            assertEquals(storingCoffeeShops.findCoffeeShop(1), loadedCoffeeShops.findCoffeeShop(1))
            assertEquals(storingCoffeeShops.findCoffeeShop(2), loadedCoffeeShops.findCoffeeShop(2))
        }
    }

    @Nested
    inner class SearchMethods {

        @Test
        fun `search coffee shops by name returns no notes when no shops with that name exist`() {
            //Searching a populated collection for a name that doesn't exist.
            assertEquals(3, populatedCoffeeShops!!.numberOfCoffeeShops())
            val searchResults = populatedCoffeeShops!!.searchByCoffeeShopName("no results expected")
            assertTrue(searchResults.isEmpty())

            //Searching an empty collection
            assertEquals(0, emptyCoffeeShops!!.numberOfCoffeeShops())
            assertTrue(emptyCoffeeShops!!.searchByCoffeeShopName("").isEmpty())
        }

        @Test
        fun `search coffee shop by name returns shops when shops with that name exist`() {
            assertEquals(3, populatedCoffeeShops!!.numberOfCoffeeShops())

            //Searching a populated collection for a full title that exists (case matches exactly)
            var searchResults = populatedCoffeeShops!!.searchByCoffeeShopName("Costa")
            assertTrue(searchResults.contains("Costa"))
            assertFalse(searchResults.contains("Test App"))

            //Searching a populated collection for a partial title that exists (case matches exactly)
            searchResults = populatedCoffeeShops!!.searchByCoffeeShopName("Cos")
            assertTrue(searchResults.contains("Costa"))
            assertTrue(searchResults.contains("Costa"))
            assertFalse(searchResults.contains("Swim"))

            //Searching a populated collection for a partial title that exists (case doesn't match)
            searchResults = populatedCoffeeShops!!.searchByCoffeeShopName("osT")
            assertTrue(searchResults.contains("Costa"))
            assertTrue(searchResults.contains("Costa"))
            assertFalse(searchResults.contains("Swim"))
        }
    }

    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfCoffeeShopsCalculatedCorrectly() {
            assertEquals(3, populatedCoffeeShops!!.numberOfCoffeeShops())
            assertEquals(0, emptyCoffeeShops!!.numberOfCoffeeShops())
        }

        @Test
        fun numberOfClosedCoffeeShopsCalculatedCorrectly() {
            assertEquals(1, populatedCoffeeShops!!.numberOfClosedCoffeeShops())
            assertEquals(0, emptyCoffeeShops!!.numberOfClosedCoffeeShops())
        }
    }
}
