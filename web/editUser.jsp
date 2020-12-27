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
                <h3>Account Management</h3>
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
                            <form accept-charset="utf-8" method="post" action="handleUser">
                                <input type="hidden" name="action" value="<%=type%>" />
                                <input type="hidden" name="id" value="<%=id%>" />
                                <div class="form-group">
                                    <label for="email">Email</label>
                                    <input type="email" name="email" id="email" placeholder="Email Address" class="form-control" required="" value="<%=email %>" <% if ("edit".equals(type)) { out.print("disabled"); } %> />
                                </div>
                                <div class="form-group">
                                    <label for="password">Password</label>
                                    <input type="password" name="password" id="password" placeholder="Password" class="form-control" required="" value="<%=password %>">
                                </div>
                                <div class="form-group">
                                    <label for="name">Name</label>
                                    <input type="text" name="name" id="name" placeholder="Name" class="form-control" required="" value="<%=name %>">
                                </div>
                                <div class="form-group">
                                    <label for="gender">Gender</label>
                                    <select name="gender" id="gender" class="form-control">
                                        <option value="M" <%if ("M".equals(gender)) { out.println("selected");}%>>Male</option>
                                        <option value="F" <%if ("F".equals(gender)) { out.println("selected");}%>>Female</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="phone">Phone Number</label>
                                    <input type="text" name="phone" id="phone" placeholder="Phone Number" class="form-control" maxlength="8" minlength="8" required="" value="<%=phone %>" />
                                </div>
                                <div class="form-group">
                                    <label for="role">Role</label>
                                    <select name="role" id="role" class="form-control">
                                        <option value="Student" <%if ("Student".equals(role)) { out.println("selected");}%>>Student</option>
                                        <option value="Technician" <%if ("Technician".equals(role)) { out.println("selected");}%>>Technician</option>
                                        <option value="Senior Technician" <%if ("Senior Technician".equals(role)) { out.println("selected");}%>>Senior Technician</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="status">Status</label>
                                    <select name="status" id="status" class="form-control">
                                        <option value="true" <% if (status) { out.println("selected"); } %>>Active</option>
                                        <option value="false" <% if (!status) { out.println("selected"); } %>>Disable</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <input type="submit" class="btn btn-primary" />
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="footer.jsp" %>