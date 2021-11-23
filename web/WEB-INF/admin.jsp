<%-- 
    Document   : admin
    Created on : Oct 12, 2021, 9:18:48 PM
    Author     : 771684
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Inventory: Admin</title>
        <link type="text/css" rel="stylesheet" href="css/stylesheet.css">

    </head>
    <body>
        <h1>Home Inventory</h1>
        <h4>Menu</h4>
        <ul style="list-style:circle">
            <li><a href="inventory">Inventory</a></li> 
            <li><a href="admin">Admin</a></li>  
            <li><a href="login?logout">Logout</a></li>  
        </ul>

        <h2>Manage Users</h2>
        <div>
            <td>
                <table>
                    <tr>
                        <th>Email</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td>${user.email}</td>
                            <td>${user.firstName}</td>
                            <td>${user.lastName}</td>
                            <td><a href="admin?action=edit&username=${user.username}">Edit</a><br></td>
                            <td><a href="admin?action=delete&username=${user.username}">Delete</a><br></td>

                        </tr>
                    </c:forEach>
                </table>
            </td>
        </div>
        <p>${message}</p>

        <h2>Add Users</h2>
        <form method="POST">
            <label>Username:</label>
            <input  class="input"type="text" name="username" placeholder="Username"><br>
            <label>Password:</label>
            <input  class="input"type="password" name="password" placeholder="Password"><br>
            <label>Email:</label>
            <input class="input" type="email" name="email" placeholder="Email"><br>
            <label>First name:</label>
            <input class="input" type="text" name="fName" placeholder="First Name"><br>
            <label>Last name:</label>
            <input class="input" type="text" name="lName" placeholder="Last Name"><br>
            <br>
            <input type="submit" value="Save"> 
            <input type="hidden" name="userAction" value="addUser">
            ${error}
        </form>        

        <h2>Edit Users</h2>
        <form method="POST">
            <label>Username:</label>
            <input  class="input"type="text" name="editUsername" placeholder="Username" value="${editUsername}" readonly><br>
            <label>Password:</label>
            <input  class="input"type="text" name="editPassword" placeholder="Password" value="${editPassword}"><br>
            <label>Email:</label>
            <input class="input" type="email" name="editEmail" placeholder="Email" value="${editEmail}"><br>
            <label>First name:</label>
            <input class="input" type="text" name="editFirstName" placeholder="First Name" value="${editFirstName}"><br>
            <label>Last name:</label>
            <input class="input" type="text" name="editLastName" placeholder="Last Name" value="${editLastName}"><br>
            <a class="checkbox" style="">Active<input class="checkbox" type="checkbox" name="activeEdit"  style="padding: 1% 1% 1% 1%;"></a> <br>
            <a class="checkbox"  style="">Admin<input class="checkbox" type="checkbox" name="adminEdit"  style="padding: 1% 1% 1% 1%;"></a> <br>
            <br>
            <input type="submit" value="Save"> 
            <input type="hidden" name="userAction" value="save" action="save">
        </form>


    </body>
</html>
