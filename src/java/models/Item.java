package models;

import java.io.Serializable;

public class Item implements Serializable {

    private int itemId;
    public Category category;
    private String itemName;
    private double itemPrice;
    private String owner;

    public Item() {
    }

    public Item(int itemId, Category category, String itemName, double itemPrice, String owner) {
        this.itemId = itemId;
        this.category = category;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.owner = owner;
    }

    public Item(Category category, String itemName, double itemPrice, String owner) {
        this.category = category;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.owner = owner;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

}
