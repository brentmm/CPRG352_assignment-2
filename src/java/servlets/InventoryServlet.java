package servlets;

import dataaccess.ItemsDB;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Item;
import services.InventoryService;

public class InventoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        //gets session
        HttpSession session = request.getSession();

        //sets local var to session value
        String username = (String) session.getAttribute("sessionUser");

        //checks if username is null if true forces login
        if (username == null || username.equals("")) {
            response.sendRedirect("login"); //redirects to login page
            return;
        }

        InventoryService is = new InventoryService();

        request.setAttribute("username", username); //sets username to display on web page
        String action = request.getParameter("action");

        if (action != null) {

            String itemId = request.getParameter("itemId");

            int int_itemId = Integer.parseInt(itemId);

            if (action.equals("delete")) {

                try {
                    Item userItem = is.get(int_itemId); //reloads table from db

                    if (username.equals(userItem.getOwner())) {
                        is.delete(int_itemId);// if action = deleted users is removed from db by email
                        List<Item> itemsList = is.get(username); //reloads table from db
                        request.setAttribute("items", itemsList);

                    } else {
                        request.setAttribute("message", "Error deleting.");
                    }

                } catch (Exception ex) {
                    Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("message", "Error deleting.");
                }
            } else {

            }
        }

        try {
            List<Item> itemsList = is.get(username);
            request.setAttribute("items", itemsList);
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Loading error");
        }

        getServletContext()
        .getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response); //loads inventory page

        return;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        ItemsDB itemDB = new ItemsDB();
        InventoryService is = new InventoryService();

        //gets session
        HttpSession session = request.getSession();

        //sets local var to session value
        String username = (String) session.getAttribute("sessionUser");

        try {
            List<Item> allItems = itemDB.getAll();
            int numItems = allItems.size();

            String category = request.getParameter("category");
            String itemName = request.getParameter("item");
            String itemPrice = request.getParameter("price");

            int intPrice = Integer.parseInt(itemPrice); //swaps item price value into an integer

            if (intPrice < 1 || intPrice > 10000) {
                request.setAttribute("username", username); //sets username to display on web page
                request.setAttribute("userCategory", category);//setting values of input boxes
                request.setAttribute("userItem", itemName);
                request.setAttribute("userPrice", itemPrice);
                List<Item> usersList = is.get(username); //if there is an error that doesnt allow an insert table is reloaded
                request.setAttribute("items", usersList);

                request.setAttribute("error", "Invalid. Please re-enter");//sets error message

                //display form again
                getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
                //after reload stop rest of execution
                return;
            }

            String userAction = request.getParameter("userAction");

            if (itemName != null || !itemName.equals("") || !itemName.equals("null") || itemPrice != null || !itemPrice.equals("") || itemPrice.matches("[0-9]+")) { //checking user entered valid price
                if (userAction.equals("addItem")) {
                    int int_category = 0;

                    switch (category) { //switch to set category on display
                        case "kitchen":
                            int_category = 1;
                            break;
                        case "bathroom":
                            int_category = 2;
                            break;
                        case "living room":
                            int_category = 3;
                            break;
                        case "basement":
                            int_category = 4;
                            break;
                        case "bedroom":
                            int_category = 5;
                            break;
                        case "garage":
                            int_category = 6;
                            break;
                        case "office":
                            int_category = 7;
                            break;
                        case "utility room":
                            int_category = 8;
                            break;
                        case "storage":
                            int_category = 9;
                            break;
                        case "other":
                            int_category = 10;
                            break;
                        default:
                            int_category = 10;
                            break;
                    }

                    double doub_itemPrice = Double.parseDouble(itemPrice);

                    //if stmt checks to make sure boxes are not empty and that the name/email meets db constraints
                    try {
                        is.insert(numItems + 1, int_category, itemName, doub_itemPrice, username); //inserts user into db table
                        List<Item> itemsList = is.get(username); //reloads updated table from db
                        request.setAttribute("items", itemsList);
                        request.setAttribute("message", "Item added!");

                    } catch (Exception ex) {
                        try {
                            List<Item> usersList = is.get(username); //if there is an error that doesnt allow an insert table is reloaded
                            request.setAttribute("items", usersList);
                            request.setAttribute("message", "error");
                        } catch (Exception ex1) {

                        }
                    }

                } else {
                    request.setAttribute("username", username); //sets username to display on web page
                    request.setAttribute("usercategory", category);//setting values of input boxes
                    request.setAttribute("userItem", itemName);
                    request.setAttribute("userPrice", itemPrice);
                    List<Item> usersList = is.get(username); //if there is an error that doesnt allow an insert table is reloaded
                    request.setAttribute("items", usersList);

                    request.setAttribute("error", "Invalid. Please re-enter");//sets error message

                    //display form again
                    getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
                    //after reload stop rest of execution
                    return;
                }
            } else {
                request.setAttribute("username", username); //sets username to display on web page
                request.setAttribute("usercategory", category);//setting values of input boxes
                request.setAttribute("userItem", itemName);
                request.setAttribute("userPrice", itemPrice);
                List<Item> usersList = is.get(username); //if there is an error that doesnt allow an insert table is reloaded
                request.setAttribute("items", usersList);

                request.setAttribute("error", "Invalid. Please input cannot be empty!");//sets error message

                getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response); //loads inventory page
                return;
            }

        } catch (Exception ex) {

            try {
                List<Item> usersList = is.get(username); //if there is an error that doesnt allow an insert table is reloaded
                request.setAttribute("username", username); //sets username to display on web page
                request.setAttribute("items", usersList);
                request.setAttribute("message", "Input cannot be empty");
            } catch (Exception ex1) {
                Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex1);
                request.setAttribute("message", "error");
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response); //loads inventory page
        return;
    }
}
