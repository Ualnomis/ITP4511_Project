<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/footerTag.tld" prefix="footerTag"%>
<%@ taglib uri="/WEB-INF/tlds/headerTag.tld" prefix="headerTag"%>
<!doctype html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Login | IVPET</title>
        <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/css/auth.css" rel="stylesheet">
    </head>

    <body>
        <div class="wrapper">
            <div class="auth-content">
                <div class="card">
                    <div class="card-body text-center">
                        <headerTag:headerIcon />
                        <h6 class="mb-4 text-muted">
                            Login to your account<br />
                        </h6>
                        <h3 style="color: red;">
                            <%
                                String error = request.getParameter("error");
                                if ((error != null)) {
                                    out.println("Invalid Email / Password");
                                }
                            %>
                        </h3>
                        <form action="login" method="POST">
                            <input type="hidden" name="action" value="authenticate" />
                            <div class="form-group text-left">
                                <label for="email">Email adress</label>
                                <input type="email" name="email" class="form-control" placeholder="Enter Email" required>
                            </div>
                            <div class="form-group text-left">
                                <label for="password">Password</label>
                                <input type="password" name="password" class="form-control" placeholder="Password" required>
                            </div>
                            <div class="form-group text-left">
                                <div class="custom-control custom-checkbox">
                                    <input type="checkbox" name="remember" class="custom-control-input" id="remember-me">
                                    <label class="custom-control-label" for="remember-me">Remember me on this device</label>
                                </div>
                            </div>
                            <button class="btn btn-primary shadow-2 mb-4">Login</button>
                        </form>
                        <p class="mb-2 text-muted">Forgot password? <a href="forgot-password.html">Reset</a></p>
                        <!--                        <p class="mb-0 text-muted">Don't have account yet? <a href="signup.html">Signup</a></p>-->
                    </div>
                </div>
            </div>
        </div>
        <footerTag:footer />
    </body>

</html>