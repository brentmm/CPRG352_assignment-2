package services;

import dataaccess.ItemsDB;
import java.util.List;
import models.Category;
import models.Item;

public class InventoryService {

    public List<Item> getAll() throws Exception {
        ItemsDB itemDB = new ItemsDB();
        List<Item> items = itemDB.getAll();
        return items;
    }

    public List<Item> get(String owner) throws Exception {
        ItemsDB itemDB = new ItemsDB();
        List<Item> items = itemDB.get(owner);
        return items;

    }

    public Item get(int itemId) throws Exception {
        ItemsDB itemDB = new ItemsDB();
        Item item = itemDB.get(itemId);
        return item;
    }

    public void insert(int itemId, int category, String itemName, double itemPrice, String owner) throws Exception {
        ItemsDB itemDB = new ItemsDB();
        itemDB.insert(itemId, category, itemName, itemPrice, owner);

    }

    public void update(int itemId, Category category, String itemName, double itemPrice, String owner) throws Exception {
        Item item = new Item(itemId, category, itemName, itemPrice, owner);
        ItemsDB itemDB = new ItemsDB();
        itemDB.update(item);

    }

    public void delete(int itemId) throws Exception {
        ItemsDB itemDB = new ItemsDB();
        itemDB.delete(itemId);
    }

}
