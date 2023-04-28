import controllers.CoffeeShopAPI
import models.CoffeeShop
import mu.KotlinLogging
import utils.ScannerInput

private val logger = KotlinLogging.logger {}

private val CoffeeShopAPI = CoffeeShopAPI()

fun main() = runMenu()

fun mainMenu() = ScannerInput.readNextInt(""" 
         > -----------------------------------------------------  
         > |          Coffee Shop Management APP               |
         > -----------------------------------------------------  
         > | Coffee Shop MENU                                  |
         > |   1) Add a coffee shop                            |
         > |   2) List all coffee shops                        |
         > |   3) Update coffee shop details                   |
         > |   4) Remove a coffee shop                         |
         > |   20) Close a coffee shop                         |
         > -----------------------------------------------------  
         > | Coffee Shop Sales MENU                            | 
         > |   5) Add a sale to a coffee shop                  |
         > |   6) Update sale details                          |
         > |   7) Delete a sale                                |
         > |   8) Mark a sale as fulfilled                     | 
         > -----------------------------------------------------  
         > | REPORT MENU FOR Coffee Shops                      | 
         > |   9) Search for all coffee shops (shop name)      |
         > -----------------------------------------------------  
         > | REPORT MENU FOR Coffee Shop Sales                 |                                
         > |   10) Search for sales (by sold item)             |
         > |   11) List fulfilled sales                        |
         > -----------------------------------------------------  
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)


fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addCoffeeShop()
            2 -> listCoffeeShops()
            3 -> updateCoffeeShop()
            4 -> removeCoffeeShop()
            5 -> addSaleToCoffeeShop()
            6 -> updateSaleDetails()
            7 -> deleteSale()
            8 -> fulfillSale()
            9 -> searchAllCoffeeShops()
            10 -> searchSalesBySold()
            11 -> listFulfilledSales()
            20 -> closeCoffeeShop()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

// Using user's input data, a note is added to NoteAPI
fun addCoffeeShop() {
    //logger.info { "addCoffeeShop() function invoked" }
    val shopName = ScannerInput.readNextLine("Enter a shop name: ")
    val shopLocation = ScannerInput.readNextLine("Enter the shop location: ")
    val shopDetails = ScannerInput.readNextLine("Enter the shop details: ")
    val dateAdded = ScannerInput.readNextLine("Enter the date the shop was added to the system: ")
    val isAdded = CoffeeShopAPI.add(CoffeeShop(shopName = shopName, shopLocation = shopLocation, shopDetails = shopDetails, dateAdded = dateAdded, isCoffeeShopClosed = false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}
fun listCoffeeShops() = println(CoffeeShopAPI.listCoffeeShops())


fun updateCoffeeShop() {
    listCoffeeShops()
    if (CoffeeShopAPI.numberOfCoffeeShops() > 0) {
        // only ask the user to choose a coffee shop if coffee shops already exist on the system
        val id = ScannerInput.readNextInt("Enter the id of the shop to update: ")
        if (CoffeeShopAPI.findCoffeeShop(id) != null) {
            val shopName = ScannerInput.readNextLine("Enter a shop name: ")
            val shopLocation = ScannerInput.readNextLine("Enter the shop location: ")
            val shopDetails = ScannerInput.readNextLine("Enter the shop details: ")
            val dateAdded = ScannerInput.readNextLine("Enter the date the shop was added to the system: ")

            // pass the index of the coffee shop and the updated details to NoteAPI for updating and check for success.
            if (CoffeeShopAPI.updateCoffeeShop(id, CoffeeShop(0, shopName, shopLocation, shopDetails, dateAdded, isCoffeeShopClosed = false))){
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
        val id = ScannerInput.readNextInt("Enter the id of the shop to remove: ")
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
        //only ask the user to choose the coffee shop to close if there are shops on the system.
        val indexToClose = ScannerInput.readNextInt("Enter the index of the shop to close: ")
        //pass the index of the shop to CoffeeShopAPI for closing and check for success.
        if (CoffeeShopAPI.closeCoffeeShop(indexToClose)) {
            println("Closure Successful!")
        } else {
            println("Closure NOT Successful")
        }
    }
}
fun addSaleToCoffeeShop() {
    logger.info { "addSaleToCoffeeShop() function invoked" }
}
fun updateSaleDetails() {
    logger.info { "updateSaleDetails() function invoked" }
}
fun deleteSale() {
    logger.info { "deleteSale() function invoked" }
}
fun fulfillSale() {
    logger.info { "fulfillSale() function invoked" }
}
fun searchAllCoffeeShops() {
    logger.info { "searchAllCoffeeShops() function invoked" }
}

fun searchSalesBySold() {
    logger.info { "searchSalesBySold() function invoked" }
}
fun listFulfilledSales() {
    logger.info { "listFulfilledSales() function invoked" }
}
fun exitApp() {
    logger.info { "exitApp() function invoked" }
}

