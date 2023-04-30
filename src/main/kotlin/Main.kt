import controllers.CoffeeShopAPI
import models.CoffeeShop
import models.CoffeeShopSales
import mu.KotlinLogging
import persistence.XMLSerializer
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextDouble
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.time.LocalDate

private val logger = KotlinLogging.logger {}

private val CoffeeShopAPI = CoffeeShopAPI(XMLSerializer(File("coffeeShops.xml")))

fun main() = runMenu()

fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |          Coffee Shop Management APP               |
         > -----------------------------------------------------  
         > | Coffee Shop and Sales CRUD                        |
         > |   1) Coffee Shop                                  |
         > |   2) Coffee Shop Sales                            |
         > -----------------------------------------------------  
         > -----------------------------------------------------  
         > | REPORT MENU FOR Coffee Shops                      | 
         > |   3) Search for all coffee shops (shop name)      |
         > ----------------------------------------------------- 
         > -----------------------------------------------------  
         > | REPORT MENU FOR Coffee Shop Sales                 |                                
         > |   4) Search for sales (by sold item)              |
         > |   5) Search sales by price                        |
         > |   6) List fulfilled sales                         |
         > -----------------------------------------------------  
         > -----------------------------------------------------  
         > |   0) Exit                                         |
         > |  98) Save                                         |
         > |  99) Load                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> listCoffeeShopCrud()
            2 -> listCoffeeShopSalesCrud()
            3 -> searchAllCoffeeShops()
            4 -> searchSales()
            5 -> searchForPrice()
            6 -> listFulfilledSales()
            0 -> exitApp()
            98 -> saveCoffeeShops()
            99 -> loadCoffeeShops()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun listCoffeeShopCrud() {
    val option = readNextInt(
        """
                 > -----------------------------------
                 > |   1) Add a coffee shop          |
                 > |   2) List all coffee shops      |
                 > |   3) Update coffee shop details |
                 > |   4) Remove a coffee shop       |
                 > |   5) Close a coffee shop        |
                 > |   0) Home                       |
                 > -----------------------------------
         > ==>> """.trimMargin(">")
    )

    when (option) {
        1 -> addCoffeeShop()
        2 -> listCoffeeShops()
        3 -> updateCoffeeShop()
        4 -> removeCoffeeShop()
        5 -> closeCoffeeShop()
        0 -> mainMenu()
        else -> println("Invalid option entered: $option")
    }
}

fun listCoffeeShopSalesCrud() {
    val option = readNextInt(
        """
                 > -----------------------------------
                 > |   1) Add a sale to coffee shop  |
                 > |   2) List all sales             |
                 > |   3) Update sale                |
                 > |   4) Remove a sale              |
                 > |   5) Mark sale as fulfilled     |
                 > |   0) Home                       |
                 > -----------------------------------
         > ==>> """.trimMargin(">")
    )

    when (option) {
        1 -> addSaleToCoffeeShop()
        2 -> listAllSales()
        3 -> updateSaleDetails()
        4 -> deleteSale()
        5 -> fulfillSale()
        0 -> mainMenu()
        else -> println("Invalid option entered: $option")
    }
}
// Using user's input data, a note is added to NoteAPI
fun addCoffeeShop() {
    // logger.info { "addCoffeeShop() function invoked" }
    val shopName = readNextLine("Enter a shop name: ")
    val shopLocation = readNextLine("Enter the shop location: ")
    val shopDetails = readNextLine("Enter the shop details: ")
    val dateAdded = LocalDate.now()
    val isAdded = CoffeeShopAPI.add(CoffeeShop(shopName = shopName, shopLocation = shopLocation, shopDetails = shopDetails, dateAdded = dateAdded, isCoffeeShopClosed = false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}
fun listCoffeeShops() {
    if (CoffeeShopAPI.numberOfCoffeeShops() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL coffee shops   |
                  > |   2) View CLOSED coffee shops|
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllCoffeeShops()
            2 -> listClosedCoffeeShops()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No coffee shops stored")
    }
}

fun listAllCoffeeShops() {
    println(CoffeeShopAPI.listAllCoffeeShops())
}

fun listClosedCoffeeShops() {
    println(CoffeeShopAPI.listClosedCoffeeShops())
}

fun updateCoffeeShop() {
    listCoffeeShops()
    if (CoffeeShopAPI.numberOfCoffeeShops() > 0) {
        // only ask the user to choose a coffee shop if coffee shops already exist on the system
        val id = readNextInt("Enter the id of the shop to update: ")
        if (CoffeeShopAPI.findCoffeeShop(id) != null) {
            val shopName = readNextLine("Enter a shop name: ")
            val shopLocation = readNextLine("Enter the shop location: ")
            val shopDetails = readNextLine("Enter the shop details: ")
            val dateAdded = LocalDate.now()

            // pass the index of the coffee shop and the updated details to NoteAPI for updating and check for success.
            if (CoffeeShopAPI.updateCoffeeShop(id, CoffeeShop(0, shopName, shopLocation, shopDetails, dateAdded, isCoffeeShopClosed = false))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

fun removeCoffeeShop() {
    listCoffeeShops()
    if (CoffeeShopAPI.numberOfCoffeeShops() > 0) {
        // only ask the user to choose a shop to remove if there are coffee shops on the system
        val id = readNextInt("Enter the id of the shop to remove: ")
        // pass the index of the coffee shop to CoffeeShopAPI for deleting and check for success.
        val coffeeShopToDelete = CoffeeShopAPI.removeCoffeeShop(id)
        if (coffeeShopToDelete != null) {
            println("Coffee Shop Removed!")
        } else {
            println("Coffee Shop Was NOT Removed!")
        }
    }
}

// mark selected note as closed
fun closeCoffeeShop() {
    listCoffeeShops()
    if (CoffeeShopAPI.numberOfCoffeeShops() > 0) {
        // only ask the user to choose the coffee shop to close if there are shops on the system.
        val indexToClose = readNextInt("Enter the index of the shop to close: ")
        // pass the index of the shop to CoffeeShopAPI for closing and check for success.
        if (CoffeeShopAPI.closeCoffeeShop(indexToClose)) {
            println("Closure Successful!")
        } else {
            println("Closure NOT Successful")
        }
    }
}

private fun addSaleToCoffeeShop() {
    val sale: CoffeeShop? = askUserToChooseCoffeeShop()
    if (sale != null) {
        val saleContents = readNextLine("\tSale Contents: ")
        val salePrice = readNextLine("\tSale Price: ").toDouble()
        if (sale.addSale(CoffeeShopSales(saleContents = saleContents, salePrice = salePrice))) {
            println("Add Successful!")
        } else {
            println("Add NOT Successful")
        }
    }
}

fun updateSaleDetails() {
    val coffeeShop: CoffeeShop? = askUserToChooseCoffeeShop()
    if (coffeeShop != null) {
        val sale: CoffeeShopSales? = askUserToChooseSale(coffeeShop)
        if (sale != null) {
            val newContents = readNextLine("Enter new sale contents: ")
            val newSalePrice = readNextLine("If the price has changed, enter the new price: ").toDouble()
            if (coffeeShop.updateSale(sale.saleID, CoffeeShopSales(saleContents = newContents, salePrice = newSalePrice))) {
                println("Sale updated")
            } else {
                println("Sale NOT updated")
            }
        } else {
            println("Invalid sale Id")
        }
    }
}

fun deleteSale() {
    val coffeeShop: CoffeeShop? = askUserToChooseCoffeeShop()
    if (coffeeShop != null) {
        val sale: CoffeeShopSales? = askUserToChooseSale(coffeeShop)
        if (sale != null) {
            val isDeleted = coffeeShop.deleteSale(sale.saleID)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}
fun fulfillSale() {
    val coffeeShop: CoffeeShop? = askUserToChooseCoffeeShop()
    if (coffeeShop != null) {
        val sale: CoffeeShopSales? = askUserToChooseSale(coffeeShop)
        if (sale != null) {
            val changeStatus: Char
            if (sale.isSaleFulfilled) {
                changeStatus = readNextChar("The sale is fulfilled...do you want to mark it as unfulfilled?")
                if ((changeStatus == 'Y') || (changeStatus == 'y'))
                    sale.isSaleFulfilled = false
            } else {
                changeStatus = readNextChar("The sale is unfulfilled...do you want to mark it as fulfilled?")
                if ((changeStatus == 'Y') || (changeStatus == 'y'))
                    sale.isSaleFulfilled = true
            }
        }
    }
}
// search for shops via shop names
fun searchAllCoffeeShops() {
    val searchTitle = readNextLine("Enter the shop name to search for: ")
    val searchResults = CoffeeShopAPI.searchByCoffeeShopName(searchTitle)
    if (searchResults.isEmpty()) {
        println("No shops found")
    } else {
        println(searchResults)
    }
}

fun searchSales() {
    val searchContents = readNextLine("Enter the sale contents to search for: ")
    val searchResults = CoffeeShopAPI.searchSaleByContents(searchContents)
    if (searchResults.isEmpty()) {
        println("No sales found")
    } else {
        println(searchResults)
    }
}

fun searchForPrice() {
    val searchPrice = readNextDouble("Enter the sale price to search for: ")
    val searchResults = CoffeeShopAPI.searchSaleByPrice(searchPrice)
    if (searchResults.isEmpty()) {
        println("No sales found from that price")
    } else {
        println(searchResults)
    }
}


fun listAllSales() {
    println(CoffeeShopAPI.listAllSales())
}

fun listFulfilledSales() {
    if (CoffeeShopAPI.numberOfFulfilledSales() > 0) {
        println("Total fulfilled sales: ${CoffeeShopAPI.numberOfFulfilledSales()}")
    }
    println(CoffeeShopAPI.listFulfilledSales())
}

fun exitApp() {
    logger.info { "exitApp() function invoked" }
}

private fun askUserToChooseCoffeeShop(): CoffeeShop? {
    listCoffeeShops()
    if (CoffeeShopAPI.numberOfCoffeeShops() > 0) {
        val sale = CoffeeShopAPI.findCoffeeShop(readNextInt("\nEnter the id of the shop you wish to choose: "))
        if (sale != null) {
            return sale // chosen shop exists
        }
    } else {
        println("Coffee Shop does not exist")
    }
    return null // Coffee Shop does not exist
}

private fun askUserToChooseSale(coffeeShop: CoffeeShop): CoffeeShopSales? {
    return if (coffeeShop.numberOfSales() > 0) {
        print(coffeeShop.listSales())
        coffeeShop.findSale(readNextInt("\nEnter the id of the sale: "))
    } else {
        println("No sales for chosen coffee shop")
        null
    }
}

fun saveCoffeeShops() {
    try {
        CoffeeShopAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun loadCoffeeShops() {
    try {
        CoffeeShopAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}
