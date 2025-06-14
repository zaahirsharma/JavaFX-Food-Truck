package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SalesData {
    private static SalesData instance;
    private final Map<String, Integer> itemPurchaseCounts = new ConcurrentHashMap<>();
    private double totalRevenue = 0.0;
    private double secretMenuRevenue = 0.0; // To track secret menu revenue

    // Hold the Food and Drink items
    private FoodItem grilledCheese1;
    private FoodItem grilledCheese2;
    private final List<Drink> drinks = new ArrayList<>();
    private final List<Drink> drinkSlots = new ArrayList<>(6); // To manage drinks in specific vending slots

    private SalesData() {
        // Initialize Food and Drink items here
        grilledCheese1 = new FoodItem("Grilled Cheese", 6.00, 32, "file:/path/to/grilled_cheese.png"); // Replace with actual path
        grilledCheese2 = new FoodItem("Grilled Cheese", 6.00, 32, "file:/path/to/grilled_cheese.png"); // Replace with actual path

        drinks.add(new Drink("Coca Cola", 4.00, 16, 16, "file:/path/to/coke.png"));       // Index 0
        drinks.add(new Drink("Sprite", 3.00, 16, 0, "file:/path/to/sprite.png"));       // Index 1
        drinks.add(new Drink("Root Beer", 3.00, 16, 0, "file:/path/to/root_beer.png")); // Index 2
        drinks.add(new Drink("Water", 2.00, 16, 16, "file:/path/to/water.png"));         // Index 3

        // Initialize drink slots (initially empty)
        for (int i = 0; i < 6; i++) {
            drinkSlots.add(null);
        }

        // Initialize purchase counts
        itemPurchaseCounts.put("Grilled Cheese", 0);
        itemPurchaseCounts.put("Coca Cola", 0);
        itemPurchaseCounts.put("Sprite", 0);
        itemPurchaseCounts.put("Root Beer", 0);
        itemPurchaseCounts.put("Water", 0);
        itemPurchaseCounts.put("Secret Menu??", 0);
    }

    public static SalesData getInstance() {
        if (instance == null) {
            synchronized (SalesData.class) {
                if (instance == null) {
                    instance = new SalesData();
                }
            }
        }
        return instance;
    }

    // Methods to access Food and Drink items
    public FoodItem getGrilledCheese1() {
        return grilledCheese1;
    }

    public FoodItem getGrilledCheese2() {
        return grilledCheese2;
    }

    public Drink getDrink(int index) {
        if (index >= 0 && index < drinks.size()) {
            return drinks.get(index);
        }
        return null;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    // Methods to update purchase counts
    public void incrementItemPurchased(String itemName) {
        itemPurchaseCounts.put(itemName, itemPurchaseCounts.getOrDefault(itemName, 0) + 1);
    }

    public int getItemPurchaseCount(String itemName) {
        return itemPurchaseCounts.getOrDefault(itemName, 0);
    }

    // Method to update revenue
    public void addRevenue(double amount) {
        totalRevenue += amount;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    // Method to specifically add revenue from secret menu items
    public void addSecretRevenue(double amount) {
        secretMenuRevenue += amount;
        totalRevenue += amount; // Also add to the total revenue
    }

    public double getSecretMenuRevenue() {
        return secretMenuRevenue;
    }

    // Method to restock a specific item (you can extend this for drinks if needed)
    public void restockItem(String itemName, int quantity) {
        if (itemName.equals("Grilled Cheese")) {
            grilledCheese1.setStock(quantity);
            grilledCheese2.setStock(quantity);
        } else {
            for (Drink drink : drinks) {
                if (drink.getName().equals(itemName)) {
                    drink.setSingleStock(quantity);
                    drink.setDoubleStock(quantity);
                    break;
                }
            }
        }
    }

    // Specific methods to update drink stock
    public void setDrinkSingleStock(String drinkName, int stock) {
        for (Drink drink : drinks) {
            if (drink.getName().equals(drinkName)) {
                drink.setSingleStock(stock);
                return;
            }
        }
    }

    public void setDrinkDoubleStock(String drinkName, int stock) {
        for (Drink drink : drinks) {
            if (drink.getName().equals(drinkName)) {
                drink.setDoubleStock(stock);
                return;
            }
        }
    }

    // Methods to manage drinks in vending slots
    public void assignDrinkToSlot(int slotIndex, String drinkName) {
        if (slotIndex >= 0 && slotIndex < drinkSlots.size()) {
            Drink foundDrink = null;
            if (drinkName != null) {
                for (Drink drink : drinks) {
                    if (drink.getName().equals(drinkName)) {
                        foundDrink = drink;
                        break;
                    }
                }
            }
            drinkSlots.set(slotIndex, foundDrink);
        } else {
            System.out.println("Error: Invalid drink slot index: " + slotIndex);
        }
    }

    public Drink getDrinkInSlot(int slotIndex) {
        if (slotIndex >= 0 && slotIndex < drinkSlots.size()) {
            return drinkSlots.get(slotIndex);
        }
        return null;
    }

    public void clearDrinkSlots() {
        for (int i = 0; i < drinkSlots.size(); i++) {
            drinkSlots.set(i, null);
        }
    }

    public void decreaseStock(String itemName) {
        if (itemName.equals("Grilled Cheese")) {
            if (grilledCheese1 != null && grilledCheese1.getStock() > 0) {
                grilledCheese1.decreaseStock();
                return;
            }
            if (grilledCheese2 != null && grilledCheese2.getStock() > 0) {
                grilledCheese2.decreaseStock();
                return;
            }
        } else {
            for (Drink drink : drinks) {
                if (drink.getName().equals(itemName)) {
                    if (drink.getSingleStock() > 0) {
                        drink.decreaseSingleStock();
                        return;
                    } else if (drink.getDoubleStock() > 0) {
                        drink.decreaseDoubleStock();
                        return;
                    }
                    break;
                }
            }
        }
        System.out.println("Warning: Stock for " + itemName + " is depleted.");
    }
}