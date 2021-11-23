package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;

public class AdminServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        HttpSession session = request.getSession(); //getting session

        String sess_username = (String) session.getAttribute("sessionUser"); //grabing session variable

        AccountService as = new AccountService();

        String action = request.getParameter("action");
        String username = request.getParameter("username");

        if (action != null) {
            if (action.equals("edit")) {
                try {
                    User user = as.get(username);
                    request.setAttribute("editUsername", user.getUsername());
                    request.setAttribute("editPassword", user.getPassword());
                    request.setAttribute("editEmail", user.getEmail()); //sets all input boxes to email users info
                    request.setAttribute("editFirstName", user.getFirstName());
                    request.setAttribute("editLastName", user.getLastName());
                    request.setAttribute("editActive", user.getActive());
                    request.setAttribute("editIsAdmin", user.getIsAdmin());

                } catch (Exception ex) {

                }
            }

            if (action.equals("delete")) {
                if (!sess_username.equals(username)) {
                    try {
                        as.delete(username);// if action = deleted users is removed from db by email
                        List<User> usersList = as.getAll(); //reloads table from db
                        request.setAttribute("users", usersList);

                    } catch (Exception ex) {

                    }
                } else {
                    request.setAttribute("message", "Cannot delete self.");
                }

            }
        }

        try {
            List<User> usersList = as.getAll();
            request.setAttribute("users", usersList);
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Loading error");
        }

        getServletContext()
        .getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response); //loads login page

        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        AccountService as = new AccountService();

        String sess_username = request.getParameter("username");
        String userAction = request.getParameter("userAction");

        if (userAction != null) {
            if (userAction.equals("save")) {

                try {
                    String username = request.getParameter("editUsername");
                    String password = request.getParameter("editPassword");
                    String email = request.getParameter("editEmail"); //retrieves values from input boxes
                    String firstName = request.getParameter("editFirstName");
                    String lastName = request.getParameter("editLastName");
                    String active = request.getParameter("activeEdit");
                    String admin = request.getParameter("adminEdit");

                    if (email.length() <= 40 && firstName != null && !firstName.equals("") && firstName.length() <= 20 && lastName != null && !lastName.equals("") && lastName.length() <= 20 && password != null && !password.equals("") && password.length() <= 20) {
                        //if stmt checks to make sure boxes are not empty and that the name/email meets db constraints 
                        boolean isActive;
                        boolean isAdmin;

                        if (active != null) {
                            isActive = true;
                        } else {
                            isActive = false;
                        }

                        if (admin != null) {
                            isAdmin = true;
                        } else {
                            isAdmin = false;
                        }

                        as.update(username, password, email, firstName, lastName, isActive, isAdmin); //updates user in db
                        List<User> usersList = as.getAll(); //reloads updated table from db
                        request.setAttribute("users", usersList);
                        request.setAttribute("message", "User Edited!");
                    } else {
                        request.setAttribute("message", "There was an error while saving a user.");
                        try {
                            List<User> usersList1; //if there is an error that doesnt allow an update table is reloaded
                            usersList1 = as.getAll();
                            request.setAttribute("users", usersList1);
                        } catch (Exception ex) {

                        }
                    }
                } catch (Exception ex) {

                }

            }

            if (userAction.equals("addUser")) {
                try {

                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    String email = request.getParameter("email"); //retrieves values from input boxes
                    String fName = request.getParameter("fName");
                    String lName = request.getParameter("lName");
                    boolean isActive = true;
                    boolean isAdmin = false;

                    if (email.length() <= 40 && fName != null && !fName.equals("") && fName.length() <= 20 && lName != null && !lName.equals("") && lName.length() <= 20 && password != null && !password.equals("") && password.length() <= 20) {
                        //if stmt checks to make sure boxes are not empty and that the name/email meets db constraints

                        try {
                            as.insert(username, password, email, fName, lName, isActive, isAdmin); //inserts user into db table
                            List<User> usersList = as.getAll(); //reloads updated table from db
                            request.setAttribute("users", usersList);
                            request.setAttribute("errorMsg", "User Added!");

                        } catch (Exception ex) {
                            try {
                                List<User> usersList = as.getAll(); //if there is an error that doesnt allow an insert table is reloaded
                                request.setAttribute("users", usersList);
                                request.setAttribute("message", "error");
                            } catch (Exception ex1) {

                            }
                        }
                    } else {
                        List<User> usersList = as.getAll(); //if there is an error that doesnt allow an insert table is reloaded
                        request.setAttribute("users", usersList);
                        request.setAttribute("message", "There was an error while adding a user.");
                    }
                } catch (Exception ex) {

                }
            }
        }

        getServletContext().getRequestDispatcher("/WEB-INF/admin.jsp").forward(request, response); //loads login page
        return;
    }

}
