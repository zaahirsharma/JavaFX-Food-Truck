package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ownerController {

    @FXML
    private Button leaveDiscount;

    @FXML
    private Button pizzaSecretButton;

    @FXML
    private Button burritoSecretButton;

    @FXML
    private Button ramenSecretButton;

    private final SalesData salesData = SalesData.getInstance();


    @FXML
    void returnMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ReadyOrder.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void pizzaSecretBuy(ActionEvent event) {
        salesData.incrementItemPurchased("Secret Menu??");
        salesData.addSecretRevenue(10.00); // Use addSecretRevenue
        System.out.println("Margarita Pizza (Secret) purchased for $10.00. Total secret items sold: " + salesData.getItemPurchaseCount("Secret Menu??"));
        System.out.println("Total Revenue: $" + String.format("%.2f", salesData.getTotalRevenue()));
        System.out.println("Secret Menu Revenue: $" + String.format("%.2f", salesData.getSecretMenuRevenue()));
    }

    @FXML
    void burritoSecretBuy(ActionEvent event) {
        salesData.incrementItemPurchased("Secret Menu??");
        salesData.addSecretRevenue(10.00); // Use addSecretRevenue
        System.out.println("Grilled Chicken Burrito (Secret) purchased for $10.00. Total secret items sold: " + salesData.getItemPurchaseCount("Secret Menu??"));
        System.out.println("Total Revenue: $" + String.format("%.2f", salesData.getTotalRevenue()));
        System.out.println("Secret Menu Revenue: $" + String.format("%.2f", salesData.getSecretMenuRevenue()));
    }

    @FXML
    void ramenSecretBuy(ActionEvent event) {
        salesData.incrementItemPurchased("Secret Menu??");
        salesData.addSecretRevenue(10.00); // Use addSecretRevenue
        System.out.println("Shirley Temple (Secret) purchased for $10.00. Total secret items sold: " + salesData.getItemPurchaseCount("Secret Menu??"));
        System.out.println("Total Revenue: $" + String.format("%.2f", salesData.getTotalRevenue()));
        System.out.println("Secret Menu Revenue: $" + String.format("%.2f", salesData.getSecretMenuRevenue()));
    }
}