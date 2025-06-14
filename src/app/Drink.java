package app;

public class Drink {
    private String name;
    private double price;
    private int singleStock;
    private int doubleStock;
    private String imagePath;

    public Drink(String name, double price, int singleStock, int doubleStock, String imagePath) {
        this.name = name;
        this.price = price;
        this.singleStock = singleStock;
        this.doubleStock = doubleStock;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getSingleStock() {
        return singleStock;
    }

    public void setSingleStock(int singleStock) {
        this.singleStock = singleStock;
    }

    public int getDoubleStock() {
        return doubleStock;
    }

    public void setDoubleStock(int doubleStock) {
        this.doubleStock = doubleStock;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void decreaseSingleStock() {
        if (this.singleStock > 0) {
            this.singleStock--;
        }
    }

    public void decreaseDoubleStock() {
        if (this.doubleStock > 0) {
            this.doubleStock--;
        }
    }
}