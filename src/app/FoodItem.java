package app;

public class FoodItem {
    private String name;
    private double price;
    private int stock;
    private String imagePath;

    public FoodItem(String name, double price, int stock, String imagePath) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void decreaseStock() {
        if (this.stock > 0) {
            this.stock--;
        }
    }
}