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
import models.User;

/**
 *
 * @author 771684
 */
public class UserDB {

    public List<User> getAll() throws Exception {
        List<User> users = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM users";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String username = rs.getString(1);
                String pass = rs.getString(2);
                String email = rs.getString(3);
                String fName = rs.getString(4);
                String lName = rs.getString(5);
                Boolean active = rs.getBoolean(6);
                Boolean isAdmin = rs.getBoolean(7);

                User user = new User(username, pass, email, fName, lName, active, isAdmin);
                users.add(user);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return users;
    }

    public User get(String username) throws Exception {
        User user = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM users WHERE username=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                String pass = rs.getString(2);
                String email = rs.getString(3);
                String fName = rs.getString(4);
                String lName = rs.getString(5);
                Boolean active = rs.getBoolean(6);
                Boolean isAdmin = rs.getBoolean(7);
                user = new User(username, pass, email, fName, lName, active, isAdmin);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return user;
    }

    public void insert(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "INSERT INTO users (username, password, email, firstname, lastname, active, isAdmin) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setBoolean(6, user.getActive());
            ps.setBoolean(7, user.getIsAdmin());            
            
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void update(User user) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE users SET password=? , email=?, firstname=?, lastname=?, active=?, isAdmin=? WHERE username=?";

        try {
            ps = con.prepareStatement(sql);             
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setBoolean(5, user.getActive());
            ps.setBoolean(6, user.getIsAdmin());  
            ps.setString(7, user.getUsername());
            
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

    public void delete(String username) throws Exception {
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        String sql = "DELETE FROM users WHERE username=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }
    }

}
