<%-- 
    Document   : login
    Created on : Oct 12, 2021, 9:19:12 PM
    Author     : 771684
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Inventory: Login</title>
    </head>
    <body>
        <h1>Home Inventory</h1>
        <h2>Login</h2>
        <form method="POST">
            <label>Username:</label>
            <!--input for username-->
            <input type="text" name="username" value="${username}">
            <br>
            <label>Password:</label>
            <!--input for password-->
            <input type="password" name="password" value="${password}">
            <br>
            <!--submit button to login-->
            <input type="submit" value="Submit">               
        </form>

        <!--output box for info/error messages-->
        <p>${message}</p>
    </body>
</html>
