package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class adminController {

    @FXML private Label ccBought;
    @FXML private Label ccTotal;
    @FXML private Button ccRestock;
    @FXML private ChoiceBox<String> doubleSlot1Choice1;
    @FXML private ChoiceBox<String> doubleSlot2Choice1;
    @FXML private Button gcRestock;
    @FXML private Label gcBought;
    @FXML private Label gcTotal;
    @FXML private Button goBack;
    @FXML private Button rbRestock;
    @FXML private Label rbBought;
    @FXML private Label rbTotal;
    @FXML private Label sBought;
    @FXML private Label scBought;
    @FXML private Label scTotal;
    @FXML private ChoiceBox<String> singleSlot1Choice1;
    @FXML private ChoiceBox<String> singleSlot2Choice1;
    @FXML private Label sTotal;
    @FXML private Button sRestock;
    @FXML private Label wBought;
    @FXML private Label wTotal;
    @FXML private Button wRestock;

    private final SalesData salesData = SalesData.getInstance();
    private final int defaultStock = 32;
    private final int defaultSingleSlotStock = 16;
    private final int defaultDoubleSlotStock = 16;
    private final Map<ChoiceBox<String>, String> previousSelections = new HashMap<>();

    @FXML
    void initialize() {
        updateSalesDataDisplay();
        populateChoiceBoxes();
        setupChoiceBoxSwapListener(doubleSlot1Choice1);
        setupChoiceBoxSwapListener(doubleSlot2Choice1);
        setupChoiceBoxSwapListener(singleSlot1Choice1);
        setupChoiceBoxSwapListener(singleSlot2Choice1);

        // Initialize previous selections
        previousSelections.put(doubleSlot1Choice1, doubleSlot1Choice1.getValue());
        previousSelections.put(doubleSlot2Choice1, doubleSlot2Choice1.getValue());
        previousSelections.put(singleSlot1Choice1, singleSlot1Choice1.getValue());
        previousSelections.put(singleSlot2Choice1, singleSlot2Choice1.getValue());
    }

    private void populateChoiceBoxes() {
        // Create a list of all drink options
        java.util.List<String> allDrinks = java.util.Arrays.asList("Coca Cola", "Water", "Sprite", "Root Beer");

        // Add all drink options to each ChoiceBox
        doubleSlot1Choice1.getItems().addAll(allDrinks);
        doubleSlot2Choice1.getItems().addAll(allDrinks);
        singleSlot1Choice1.getItems().addAll(allDrinks);
        singleSlot2Choice1.getItems().addAll(allDrinks);

        // Set initial values based on current drink assignments 
        if (salesData.getDrink(0) != null && salesData.getDrink(0).getName().equals("Coca Cola")) {
            doubleSlot1Choice1.setValue("Coca Cola");
        } else if (salesData.getDrink(3) != null && salesData.getDrink(3).getName().equals("Coca Cola")) {
            doubleSlot2Choice1.setValue("Coca Cola");
        }
        if (salesData.getDrink(0) != null && salesData.getDrink(0).getName().equals("Water")) {
            doubleSlot1Choice1.setValue("Water");
        } else if (salesData.getDrink(3) != null && salesData.getDrink(3).getName().equals("Water")) {
            doubleSlot2Choice1.setValue("Water");
        }
        if (salesData.getDrink(1) != null && salesData.getDrink(1).getName().equals("Sprite")) {
            singleSlot1Choice1.setValue("Sprite");
        } else if (salesData.getDrink(2) != null && salesData.getDrink(2).getName().equals("Sprite")) {
            singleSlot2Choice1.setValue("Sprite");
        }
        if (salesData.getDrink(1) != null && salesData.getDrink(1).getName().equals("Root Beer")) {
            singleSlot1Choice1.setValue("Root Beer");
        } else if (salesData.getDrink(2) != null && salesData.getDrink(2).getName().equals("Root Beer")) {
            singleSlot2Choice1.setValue("Root Beer");
        }
        // If no initial drink is assigned, set to "None" or the first option
        if (doubleSlot1Choice1.getValue() == null) doubleSlot1Choice1.setValue("None");
        if (doubleSlot2Choice1.getValue() == null) doubleSlot2Choice1.setValue("None");
        if (singleSlot1Choice1.getValue() == null) singleSlot1Choice1.setValue("None");
        if (singleSlot2Choice1.getValue() == null) singleSlot2Choice1.setValue("None");
    }

    @FXML
    private void restockGC(ActionEvent event) {
        salesData.restockItem("Grilled Cheese", defaultStock);
        updateSalesDataDisplay();
        System.out.println("Grilled Cheese restocked to " + defaultStock);
    }

    @FXML
    private void restockDrink(ActionEvent event) {
        Button restockButton = (Button) event.getSource();
        String drinkName = "";

        if (restockButton == ccRestock) {
            drinkName = "Coca Cola";
        } else if (restockButton == sRestock) {
            drinkName = "Sprite";
        } else if (restockButton == wRestock) {
            drinkName = "Water";
        } else if (restockButton == rbRestock) {
            drinkName = "Root Beer";
        }

        if (!drinkName.isEmpty()) {
            boolean isDoubleSlot = false;
            if (drinkName.equals("Coca Cola") || drinkName.equals("Water")) {
                isDoubleSlot = true;
            }

            if (isDoubleSlot) {
                salesData.setDrinkDoubleStock(drinkName, defaultDoubleSlotStock);
                salesData.setDrinkSingleStock(drinkName, defaultSingleSlotStock);
                System.out.println(drinkName + " restocked in double and single slots to " + defaultDoubleSlotStock);
            } else {
                salesData.setDrinkSingleStock(drinkName, defaultSingleSlotStock);
                System.out.println(drinkName + " restocked in single slot to " + defaultSingleSlotStock);
            }
            updateSalesDataDisplay();
        }
    }

    @FXML
    private void leaveAdmin(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReadyOrder.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void updateSalesDataDisplay() {
        gcBought.setText("Sold: " + salesData.getItemPurchaseCount("Grilled Cheese"));
        ccBought.setText("Sold: " + salesData.getItemPurchaseCount("Coca Cola"));
        sBought.setText("Sold: " + salesData.getItemPurchaseCount("Sprite"));
        wBought.setText("Sold: " + salesData.getItemPurchaseCount("Water"));
        rbBought.setText("Sold: " + salesData.getItemPurchaseCount("Root Beer"));
        scBought.setText("Sold: " + salesData.getItemPurchaseCount("Secret Menu??"));

        gcTotal.setText("$" + String.format("%.2f", salesData.getItemPurchaseCount("Grilled Cheese") * 6.00));
        ccTotal.setText("$" + String.format("%.2f", salesData.getItemPurchaseCount("Coca Cola") * 4.00));
        sTotal.setText("$" + String.format("%.2f", salesData.getItemPurchaseCount("Sprite") * 3.00));
        wTotal.setText("$" + String.format("%.2f", salesData.getItemPurchaseCount("Water") * 2.00));
        rbTotal.setText("$" + String.format("%.2f", salesData.getItemPurchaseCount("Root Beer") * 3.00));
        scTotal.setText("$" + String.format("%.2f", salesData.getItemPurchaseCount("Secret Menu??") * 10.00));
    }

    private void setupChoiceBoxSwapListener(ChoiceBox<String> currentBox) {
        currentBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                for (Map.Entry<ChoiceBox<String>, String> entry : previousSelections.entrySet()) {
                    ChoiceBox<String> otherBox = entry.getKey();
                    String previousValueInOtherBox = entry.getValue();

                    if (otherBox != currentBox && newValue.equals(previousValueInOtherBox)) {
                        currentBox.setValue(previousValueInOtherBox);
                        otherBox.setValue(oldValue);

                        previousSelections.put(currentBox, previousValueInOtherBox);
                        previousSelections.put(otherBox, oldValue);
                        return;
                    }
                }
                previousSelections.put(currentBox, newValue);
            }
        });
    }
}