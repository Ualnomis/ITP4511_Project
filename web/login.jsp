<%-- 
    Document   : index
    Created on : Dec 20, 2020, 3:08:08 PM
    Author     : 2689
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <link href="lib/css/login.css" rel="stylesheet" type="text/css"/>
        <title>Login</title>
    </head>
    <body>

        <div class="wrapper fadeInDown">
            <div id="formContent">
                <!-- Tabs Titles -->

                <!-- Icon -->
                <div class="fadeIn first">
                    <img src="./img/VTC.png" id="icon" alt="User Icon" />
                </div>

                <!-- Login Form -->
                <form method="POST" action="login">
                    <input type="hidden" name="action" value="authenticate" />
                    <input type="text" id="userID" class="fadeIn second" name="userID" placeholder="User ID" />
                    <input type="text" id="password" class="fadeIn third password" name="password" placeholder="Password" />
                    <input type="submit" class="fadeIn fourth" value="LogIn" />
                </form>

                <!-- Remind Password -->
                <div id="formFooter">
                    <a class="underlineHover" href="#">Forgot Password?</a>
                </div>

            </div>
        </div>
    </body>
</html>
