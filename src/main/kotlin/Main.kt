import controllers.CoffeeShopAPI
import utils.ScannerInput

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
            //1 -> addCoffeeShop()
            //2 -> listCoffeeShops()
            //3 -> updateCoffeeShop()
            //4 -> removeCoffeeShop()
            //5 -> addSaleToCoffeeShop()
            //6 -> updateSaleDetails()
            //7 -> deleteSale()
            //8 -> fulfillSale()
            //9 -> searchAllCoffeeShops()
            //10 -> searchSalesBySold()
            //11 -> listFulfilledSales()
            //0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}
