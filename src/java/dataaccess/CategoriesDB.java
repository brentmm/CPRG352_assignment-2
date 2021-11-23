
package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import models.Category;

public class CategoriesDB {
    
      public List<Category> getAll() throws Exception {
        List<Category> items = new ArrayList<>();
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM categories";

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int categoryId = rs.getInt(1);
                String categoryName = rs.getString(2);                
                Category category = new Category(categoryId, categoryName );
                items.add(category);
            }
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return items;
    }
      
       public Category get(int categoryId) throws Exception {
        Category itemCategory = null;
        ConnectionPool cp = ConnectionPool.getInstance();
        Connection con = cp.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM categories WHERE categoryId=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, categoryId);
            rs = ps.executeQuery();
            if (rs.next()) {
                String categoryName = rs.getString(2);

                itemCategory = new Category(categoryId, categoryName);
            }

        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            cp.freeConnection(con);
        }

        return itemCategory;

    }

    
}
