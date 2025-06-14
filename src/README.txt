# Foodie Vending Machine Application

This JavaFX application simulates a food and drink vending machine. It provides a user interface for customers to purchase items and an admin interface for managing the inventory.

## Features

* **Customer Interface:**
    * Displays available food and drink items with their prices and stock levels.
    * Allows customers to purchase items, updating the stock and total revenue.
    * Manages drink slots with single and double stock options, simulating real-world vending machine columns.
    * Provides a "secret menu" accessible via an owner interface, adding a fun, hidden feature.
* **Admin Interface:**
    * Displays detailed sales data, including the number of items sold and total revenue for each item, enabling effective business analysis.
    * Allows administrators to restock food and drink items to maintain availability.
    * Enables administrators to assign drinks to specific vending slots and manage drink options, providing flexibility in machine configuration.
* **Data Management:**
    * Uses a `SalesData` singleton to manage item stock, sales, and revenue, ensuring data consistency across the application.
    * Keeps track of both total revenue and revenue from secret menu items, allowing for separate analysis of these sales.
* **Navigation:**
    * Uses JavaFX and FXML for UI design and navigation between different scenes, creating a modular and maintainable structure. The scenes include:
        * `Beginning`: The entry point of the application.
        * `Ready Order`: The main screen for order selection.
        * `Menu`: The customer-facing menu for item purchases.
        * `Admin`: The interface for administrative tasks.
        * `Owner`:  The interface to access the secret menu.

## Files Included

* `App.java`: The main application class that starts the JavaFX application.
* `beginningController.java`: Controller for the initial screen, allowing users to approach the truck or access the owner options.
* `readyOrderController.java`: Controller for the main order screen, providing options to go to the menu, return to the beginning, or access the admin panel.
* `menuController.java`: Controller for the customer menu, handling item purchases and displaying stock.
* `adminController.java`: Controller for the admin interface, managing stock, drink slot assignments, and sales data.
* `ownerController.java`: Controller for the owner interface, providing access to the secret menu.
* `FoodItem.java`: Class representing a food item with properties like name, price, stock, and image path.
* `Drink.java`: Class representing a drink item with properties like name, price, single stock, double stock, and image path.
* `SalesData.java`: Singleton class managing the sales data, including item stock, purchase counts, revenue, and drink slot assignments.
* `build.sh`: A shell script to compile the Java files and run the JavaFX application.

## How to Run

1.  Ensure you have JavaFX properly configured in your Java development environment. This is crucial as JavaFX is required for the graphical user interface.
2.  Use the provided `build.sh` script to compile the Java files and run the application.
    * Make the script executable: `chmod +x build.sh`
    * Run the script: `./build.sh`
3.  The `build.sh` script automates the following:
    * Compiles the Java files using `javac`, including the necessary JavaFX modules.
    * Creates the output directory structure (`out/`) to organize the compiled files, view files (FXML), resources (images), and stylesheets (CSS).
    * Copies the FXML files (`.fxml`) from the `view/` directory to the `out/view/` directory. These files define the user interface layouts.
    * Copies image files from the `resources/` directory to the `out/resources/` directory.
    * Copies CSS files from the `style/` directory to the `out/style/` directory for styling the application.
    * Runs the application using `java`, specifying the module path and classpath.

## Notes

* **Image Paths:** Remember to replace the placeholder image paths in `FoodItem.java` and `Drink.java` (e.g., `"file:/path/to/grilled_cheese.png"`) with the correct paths to your image files. Otherwise, images will not display correctly in the application.
* **FXML Files:** The application relies on FXML files for its user interface. These files (e.g., `Beginning.fxml`, `Menu.fxml`) should be located in the `view/` directory.
* **Singleton Pattern:** The `SalesData` class implements the singleton pattern. This ensures that only one instance of `SalesData` exists, providing a central point for managing the application's data.  This is important for data consistency.
* **JavaFX Configuration:** JavaFX is no longer included in the standard Java JDK since version 11.  You must download and configure it separately. The `build.sh` script assumes that the JavaFX SDK is located at `/Applications/JavaFX/javafx-sdk-24.0.1/lib`.  You'll need to adjust the `JAVA_FX_PATH` variable in the script if your JavaFX SDK is in a different location.
* **Error Handling:** While the code provides the basic vending machine functionality, consider adding more robust error handling (e.g., handling invalid input, file not found exceptions) for a production-ready application.