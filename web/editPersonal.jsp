<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.UserBean"%>
<%@include file="header.jsp" %>
<jsp:useBean id="editUser" class="ict.bean.UserBean" scope="request"/>
<%
    String type = editUser.getUserID() > 0 ? "edit" : "add";

    String id = editUser.getUserID() > 0 ? editUser.getUserID() + "" : "";

    String name = editUser.getName() != null ? editUser.getName() : "";

    String password = editUser.getPassword() != null ? editUser.getPassword() : "";

    String role = editUser.getRole() != null ? editUser.getRole() : "";

    String gender = editUser.getGender() != null ? editUser.getGender() : "";

    boolean status = (!editUser.isStatus()) && "edit".equals(type) ? false : true;

    String email = editUser.getEmail() != null ? editUser.getEmail() : "";

    String phone = editUser.getPhone() != null ? editUser.getPhone() : "";
%>
<section id="edit-user">
    <div class="content">
        <div class="container">
            <div class="page-title">
                <h3>Edit Password</h3>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <%
                                if ("add".equals(type)) {
                                    out.println("Add User");
                                } else {
                                    out.println("Edit User");
                                }
                            %>
                        </div>
                        <div class="card-body">
                            <h5 class="card-title"></h5>
                            <form accept-charset="utf-8" method="post" action="handleUser" class="needs-validation" novalidate>
                                <input type="hidden" name="action" value="<%=type%>" />
                                <input type="hidden" name="status" value="<%=status%>" />
                                <input type="hidden" name="role" value="<%=role%>" />
                                <input type="hidden" name="gender" value="<%=gender%>" />
                                <input type="hidden" name="name" value="<%=name%>" />
                                <input type="hidden" name="id" value="<%=id%>" />
                                <input type="hidden" name="phone" value="<%=phone%>" />
                                <div class="form-group">
                                    <label for="email">Email</label>
                                    <input type="email" name="email" id="email" placeholder="Email Address" class="form-control" required value="<%=email%>" <% if ("edit".equals(type)) {
                                            out.print("disabled");
                                        }%> />
                                    <div class="invalid-feedback">
                                        Please input valid email.
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="password">Password</label>
                                    <input type="password" name="password" id="password" placeholder="Password" class="form-control" required value="<%=password%>">
                                    <div class="invalid-feedback">
                                        Please input valid password.
                                    </div>
                                </div>
                                <div class="form-group">
                                    <input type="submit" value="<%= ("add".equals(type)) ? "Add" : "Edit"%>" class="btn btn-primary" />
                                    <a href="handleUser?action=list" class="btn btn-danger">Cancel</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<script>
// Example starter JavaScript for disabling form submissions if there are invalid fields
    (function () {
        'use strict';
        window.addEventListener('load', function () {
// Fetch all the forms we want to apply custom Bootstrap validation styles to
            var forms = document.getElementsByClassName('needs-validation');
// Loop over them and prevent submission
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();
</script>
<%@include file="footer.jsp" %>