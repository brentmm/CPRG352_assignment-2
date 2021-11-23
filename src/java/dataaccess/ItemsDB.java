/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Category;
import models.Item;

/**
 *
 * @author 771684
 */
public class ItemsDB {

    public List<Item> get(String username) throws Exception {
        List<Item> items = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM items WHERE owner=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            CategoriesDB itemCategory = new CategoriesDB();
            while (rs.next()) {
                int itemId = rs.getInt(1);              
                Category category = itemCategory.get(rs.getInt(2));
                String itemName = rs.getString(3);
                double itemPrice = rs.getDouble(4);
                String owner = rs.getString(5);              
               
                
                Item item = new Item(itemId, category, itemName, itemPrice, owner);
                items.add(item);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return items;
    }

    public Item get(int item) throws Exception {
        Item userItem = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        CategoriesDB itemCategory = new CategoriesDB();

        String sql = "SELECT * FROM items WHERE itemId=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, item);
            rs = ps.executeQuery();
            if (rs.next()) {
                int itemId = rs.getInt(1);
                Category category = itemCategory.get(rs.getInt(2));
                String itemName = rs.getString(3);
                double itemPrice = rs.getDouble(4);
                String owner = rs.getString(5);

                userItem = new Item(itemId, category , itemName, itemPrice, owner);
            }

        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return userItem;

    }

    public List<Item> getAll() throws Exception {
        List<Item> items = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM items";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery(); 
            CategoriesDB itemCategory = new CategoriesDB();
            while (rs.next()) {
                int itemId = rs.getInt(1);
                Category category = itemCategory.get(rs.getInt(2));
                String itemName = rs.getString(3);
                double itemPrice = rs.getDouble(4);
                String owner = rs.getString(5);
                Item item = new Item(itemId, category, itemName, itemPrice, owner);
                items.add(item);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return items;
    }

    public void insert(int itemId, int category, String itemName, double itemPrice, String owner) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;        
        String sql = "INSERT INTO items (itemId, category, itemName, Price, owner) VALUES (?, ?, ?, ?, ?)";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, itemId);
            ps.setInt(2, category);
            ps.setString(3, itemName);
            ps.setDouble(4, itemPrice);
            ps.setString(5, owner);
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void update(Item item) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE items SET Category=?, itemName=?, itemPrice=? WHERE itemId=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, item.category.getCategoryId());
            ps.setString(2, item.getItemName());
            ps.setDouble(3, item.getItemPrice());
            ps.setInt(4, item.getItemId());
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void delete(int itemId) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM items WHERE itemId=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, itemId);
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

}
