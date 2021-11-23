package services;

import dataaccess.ItemsDB;
import dataaccess.UserDB;
import java.util.List;
import models.Item;
import models.User;

/**
 *
 * @author Dynamic Duo
 */
public class AccountService {

    public User get(String username) throws Exception {
        UserDB userDB = new UserDB();
        User user = null;

        try {
            user = userDB.get(username);
            
        } catch(Exception ex) {
            
        }

        return user;
    }

    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;

    }

    public void insert(String username, String pass, String email, String fName, String lName, boolean active, boolean isAdmin) throws Exception {
        User user = new User(username, pass, email, fName, lName, active, isAdmin);
        UserDB userDB = new UserDB();
        userDB.insert(user);

    }

    public void update(String username, String pass, String email, String fName, String lName, boolean active, boolean isAdmin) throws Exception {
        User user = new User(username, pass, email, fName, lName, active, isAdmin);
        UserDB userDB = new UserDB();
        userDB.update(user);

    }

    public void delete(String username) throws Exception {
        UserDB userDB = new UserDB();
        ItemsDB itemDB = new ItemsDB();
        
        List<Item> userItems = itemDB.get(username);
        
        for (int i = 0; i < userItems.size(); i++){
            Item item = userItems.get(i);
            int itemId = item.getItemId();
            itemDB.delete(itemId);
        }
          
        
        userDB.delete(username);
        
    }

}
