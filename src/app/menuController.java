package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class menuController {
    @FXML
    private ImageView imageView;

    private final SalesData salesData = SalesData.getInstance();

    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% BEGIN Grilled Cheese Management%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    private FoodItem grCheese1;
    private FoodItem grCheese2;
    private final int initialGrilledCheeseStock = 32;

    @FXML
    private Button grilledCheese;

    @FXML
    private TextField grilledCheeseStockText1;
    @FXML
    private TextField grilledCheeseStockText2;

    @FXML
    private void buyGrilledCheese(ActionEvent event) {
        if (grCheese1.getStock() >= grCheese2.getStock()) {
            if (grCheese1.getStock() > 0) {
                grCheese1.setStock(grCheese1.getStock() - 1);
            } else if (grCheese2.getStock() > 0) {
                grCheese2.setStock(grCheese2.getStock() - 1);
            }
        } else {
            if (grCheese2.getStock() > 0) {
                grCheese2.setStock(grCheese2.getStock() - 1);
            } else if (grCheese1.getStock() > 0) {
                grCheese1.setStock(grCheese1.getStock() - 1);
            }
        }

        salesData.incrementItemPurchased("Grilled Cheese");
        salesData.addRevenue(6.00);
        updateGrilledCheeseStock();
        updateVisibility();
    }

    private void updateVisibility() {
        if (grCheese1.getStock() <= 0 && grCheese2.getStock() <= 0) {
            grilledCheese.setVisible(false);
            grilledCheese.setManaged(false);
        } else {
            grilledCheese.setVisible(true);
            grilledCheese.setManaged(true);
        }
    }

    private void updateGrilledCheeseStock() {
        if (grCheese1.getStock() <= 0) {
            grilledCheeseStockText1.setText("OUT");
        } else {
            grilledCheeseStockText1.setText(grCheese1.getStock() + " left");
        }

        if (grCheese2.getStock() <= 0) {
            grilledCheeseStockText2.setText("OUT");
        } else {
            grilledCheeseStockText2.setText(grCheese2.getStock() + " left");
        }
    }

    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% END Grilled Cheese Management%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% BEGIN Drink Management %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    private Drink[] drinkItems = new Drink[4];
    private final int initialSingleDrinkStock = 16;
    private final int initialDoubleDrinkStock = 16;
    private Button[] buyButtons;

    @FXML
    private TextField doubleDrink1Stock1;
    @FXML
    private TextField doubleDrink1Stock2;
    @FXML
    private TextField doubleDrink2Stock1;
    @FXML
    private TextField doubleDrink2Stock2;
    @FXML
    private TextField singleDrink1Stock;
    @FXML
    private TextField singleDrink2Stock;


    @FXML
    private Button buyDrinkButton1;
    @FXML
    private Button buyDrinkButton2;
    @FXML
    private Button buyDrinkButton3;
    @FXML
    private Button buyDrinkButton4;

    private boolean firstDoubleSlot1UsedLast = false;
    private boolean lastDoubleSlot1UsedLast = false;

    @FXML
    private void buyDrink(ActionEvent event) {
        for (int i = 0; i < buyButtons.length; i++) {
            if (event.getSource() == buyButtons[i]) {
                buyDynamicDrink(i);
                break;
            }
        }
    }

    private void buyDynamicDrink(int drinkIndex) {
        if (drinkIndex >= 0 && drinkIndex < drinkItems.length) {
            Drink drink = drinkItems[drinkIndex];

            if ((drinkIndex == 0 || drinkIndex == 3)) { // Coke or Water (Double Slot)
                if (drink.getDoubleStock() >= drink.getSingleStock()) {
                    if (drink.getDoubleStock() > 0){
                        drink.setDoubleStock(drink.getDoubleStock() - 1);
                    } else if (drink.getSingleStock() > 0){
                        drink.setSingleStock(drink.getSingleStock() - 1);
                    }
                } else {
                    if (drink.getSingleStock() > 0){
                        drink.setSingleStock(drink.getSingleStock() - 1);
                    } else if (drink.getDoubleStock() > 0){
                        drink.setDoubleStock(drink.getDoubleStock() - 1);
                    }
                }
                salesData.incrementItemPurchased(drink.getName());
                salesData.addRevenue(drink.getPrice());
                updateDoubleSlotUI(drinkIndex); // Update the visual slots

            } else if (drink.getSingleStock() > 0 && (drinkIndex == 1 || drinkIndex == 2)) { // Sprite or Root Beer (Single Slot)
                drink.setSingleStock(drink.getSingleStock() - 1);
                salesData.incrementItemPurchased(drink.getName());
                salesData.addRevenue(drink.getPrice());
                updateSingleSlotUI(drinkIndex); // Update the single slot UI
            }

            updateDrinkVisibility(drink, buyButtons[drinkIndex]);
        }
    }

    public void updateDrinks() {
        for (int i = 0; i < drinkItems.length; i++) {
            Drink drink = drinkItems[i];
            if (i == 0) { // Coke (Double - UI managed in updateDoubleSlotUI)
                updateDoubleSlotUI(i);
            } else if (i == 1) { // Sprite (Single)
                updateStockText(singleDrink1Stock, drink.getSingleStock());
            } else if (i == 2) { // Root Beer (Single)
                updateStockText(singleDrink2Stock, drink.getSingleStock());
            } else if (i == 3) { // Water (Double - UI managed in updateDoubleSlotUI)
                updateDoubleSlotUI(i);
            }
            updateDrinkVisibility(drink, buyButtons[i]);
        }
    }

    private void updateSingleSlotUI(int drinkIndex) {
        Drink drink = drinkItems[drinkIndex];
        if (drinkIndex == 1) { // Sprite
            updateStockText(singleDrink1Stock, drink.getSingleStock());
        } else if (drinkIndex == 2) { // Root Beer
            updateStockText(singleDrink2Stock, drink.getSingleStock());
        }
    }

    private void updateDoubleSlotUI(int drinkIndex) {
        Drink drink = drinkItems[drinkIndex];
        if (drinkIndex == 0) { // Coke
            updateStockText(doubleDrink1Stock1, drink.getDoubleStock());
            updateStockText(doubleDrink1Stock2, drink.getSingleStock());
        } else if (drinkIndex == 3) { // Water
            updateStockText(doubleDrink2Stock1, drink.getDoubleStock());
            updateStockText(doubleDrink2Stock2, drink.getSingleStock());
        }
    }

    private void updateStockText(TextField textField, int stock) {
        if (textField != null) {
            if (stock <= 0) {
                textField.setText("OUT");
            } else {
                textField.setText(stock + " Left");
            }
        }
    }

    private void updateDrinkVisibility(Drink drink, Button buyButton) {
        if (drink == drinkItems[0]) { // Coke (Double)
            if (drink.getDoubleStock() <= 0 && drink.getSingleStock() <= 0) {
                buyButton.setVisible(false);
                buyButton.setManaged(false);
            } else {
                buyButton.setVisible(true);
                buyButton.setManaged(true);
            }
        } else if (drink == drinkItems[3]) { // Water (Double)
            if (drink.getDoubleStock() <= 0 && drink.getSingleStock() <= 0) {
                buyButton.setVisible(false);
                buyButton.setManaged(false);
            } else {
                buyButton.setVisible(true);
                buyButton.setManaged(true);
            }
        } else { // Single slotted drinks (Sprite, Root Beer)
            if (drink.getSingleStock() <= 0) {
                buyButton.setVisible(false);
                buyButton.setManaged(false);
            } else {
                buyButton.setVisible(true);
                buyButton.setManaged(true);
            }
        }
    }


    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% END Drink Management%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

    @FXML
    private Button backButton;

    @FXML
    private void goBack(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReadyOrder.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void initialize() {
        // Initialize Grilled Cheese stock
        grCheese1 = new FoodItem("Grilled Cheese", 6.00, initialGrilledCheeseStock, "file:/Users/zsharma/Documents/Sophmore_Aggie/CSCE_314/Java/JavaFXProject/src/resources/grilled_cheese.png");
        grCheese2 = new FoodItem("Grilled Cheese", 6.00, initialGrilledCheeseStock, "file:/Users/zsharma/Documents/Sophmore_Aggie/CSCE_314/Java/JavaFXProject/src/resources/grilled_cheese.png");
        updateGrilledCheeseStock();
        updateVisibility();

        // Initialize Drink stocks
        drinkItems[0] = new Drink("Coca Cola", 4.00, initialSingleDrinkStock, initialDoubleDrinkStock, "file:/Users/zsharma/Documents/Sophmore_Aggie/CSCE_314/Java/JavaFXProject/src/resources/coke.png");
        drinkItems[1] = new Drink("Sprite", 3.00, initialSingleDrinkStock, 0, "file:/Users/zsharma/Documents/Sophmore_Aggie/CSCE_314/Java/JavaFXProject/src/resources/sprite.png");
        drinkItems[2] = new Drink("Root Beer", 3.00, initialSingleDrinkStock, 0, "file:/Users/zsharma/Documents/Sophmore_Aggie/CSCE_314/Java/JavaFXProject/src/resources/root_beer.png");
        drinkItems[3] = new Drink("Water", 2.00, initialSingleDrinkStock, initialDoubleDrinkStock, "file:/Users/zsharma/Documents/Sophmore_Aggie/CSCE_314/Java/JavaFXProject/src/resources/water.png");

        // Initialize UI element arrays
        TextField[] doubleStockDisplays = {doubleDrink1Stock1, doubleDrink1Stock2, doubleDrink2Stock1, doubleDrink2Stock2};
        TextField[] singleStockDisplays = {singleDrink1Stock, singleDrink2Stock};
        Button[] buyButtons = {buyDrinkButton1, buyDrinkButton2, buyDrinkButton3, buyDrinkButton4};
        this.buyButtons = buyButtons; // Assign to the class field

        updateDrinks(); // Call this to set initial drink stock text and button visibility
    }

}