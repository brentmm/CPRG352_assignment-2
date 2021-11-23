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

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        HttpSession session = request.getSession(); //getting session

        String s_username = (String) session.getAttribute("sessionUser"); //grabing session variable

        if (request.getQueryString() != null) { //checking if user selected to logout
            if (request.getQueryString().equals("logout")) {
                session.invalidate(); //destroying session
                request.setAttribute("message", "You have sucessfully logged out."); //setting message to notify user of logout
                getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response); //loading login page
                return;

            }
        }

        if (s_username != null) { //checking if user is logged in, if true redirects to home page
            response.sendRedirect("inventory");
            return;
        }

        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response); //loads login page
        return;

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String username = request.getParameter("username"); //fills login page input boxes
        String password = request.getParameter("password");
        
        if (username == null || username.equals("") || password == null || password.equals("")) { //checking user entered username and pass
            request.setAttribute("username", username);//setting values of textboxes
            request.setAttribute("password", password);
            request.setAttribute("message", "Please enter your username and password");
            //display form again
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            //after reload stop rest of execution
            return;
        }
        
        AccountService as = new AccountService();
        List<User> userData = null;
        
        try {
            userData = as.getAll();
        } catch (Exception ex) {
            Logger.getLogger(AdminServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Login Error");
            return;
        }

        //validate login
        for (int n = 0; n < userData.size(); n++) { //loops through comaparing login information to what the user entered
            if (userData.get(n).getUsername().equals(username) && userData.get(n).getPassword().equals(password) && userData.get(n).getIsAdmin().equals(true) ) {
                HttpSession session = request.getSession(); //getting session

                session.setAttribute("sessionUser", username); //setting session variable to username
                session.setAttribute("sessionPass", password);
                session.setAttribute("sessionRole", true);

                response.sendRedirect("admin"); //redirecting to admin page
                return;

            } else if (userData.get(n).getUsername().equals(username) && userData.get(n).getPassword().equals(password)) {

                HttpSession session = request.getSession(); //getting session

                session.setAttribute("sessionUser", username); //setting session variable to username
                session.setAttribute("sessionPass", password);
                session.setAttribute("sessionRole", false);

                response.sendRedirect("inventory"); //redirecting to home page
                return;

            }

        }

        request.setAttribute("message", "Invalid login info");
        //display form again
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        //after reload stop rest of execution
        return;

    }

}
