<%-- 
    Document   : header
    Created on : Dec 19, 2020, 11:07:54 PM
    Author     : 2689
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
        <link href="lib/css/style.css" rel="stylesheet" />
    </head>
    <body>
        <jsp:useBean id="user" class="ict.bean.UserBean" scope="session" />
        <% if ("Student".equals(user.getRole())) { %>
        <section id="navbar">
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <div class="container">
                    <a class="navbar-brand" href="#">IVPET Equipment</a>
                    <form method="post" action="login">
                        <span class="navbar-text">
                            Welcome! <jsp:getProperty name="user" property="name" />
                        </span>
                        <input class="nav-item" type="hidden" name="action" value="logout" />
                        <input class="btn btn-danger" type="submit" value="Logout" name="logoutButton" />
                    </form>
                </div>
            </nav>
        </section>
        <% } else if ("Technician".equals(user.getRole())) { %>
        test
        <% } else if ("Senior Technician".equals(user.getRole())) { %>
        <section id="navbar">
            <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                <div class="container">
                    <a class="navbar-brand" href="#">IVPET Equipment</a>
                    <div class="collapse navbar-collapse">
                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                
                                <a href="handleUser?action=list" class="nav-link">Account Management</a>
                            </li>
                        </ul>  

                        <form class="d-flex" method="post" action="login">
                            <span class="navbar-text">
                                Welcome! <jsp:getProperty name="user" property="name" />
                            </span>
                            <input class="nav-item" type="hidden" name="action" value="logout" />
                            <input class="btn btn-danger" type="submit" value="Logout" name="logoutButton" />
                        </form>
                    </div>
                </div>
            </nav>
        </section>
        
        
        <%}%>