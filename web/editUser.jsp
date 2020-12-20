<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.UserBean"%>
<%@include file="header.jsp" %>
<jsp:useBean id="editUser" class="ict.bean.UserBean" scope="request"/>
<%
    String type = editUser.getUserID() > 0 ? "edit" : "add";
%>

<%
    String id = editUser.getUserID() > 0 ? editUser.getUserID() + "" : "";
%>

<%
    String name = editUser.getName() != null ? editUser.getName() : "";
%>

<%
    String password = editUser.getPw() != null ? editUser.getPw() : "";
%>

<%
    String role = editUser.getRole() != null ? editUser.getRole() : "";
%>
<section id="edit-user">
    <div class="container">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <div class="d-flex">
                        <% if (type.equals("edit")) { %>
                        Edit User
                        <% } else { %>
                        Add User
                        <% }%>
                    </div>
                </div>
                <div class="card-body">
                    <form method="post" action="handleUserEdit">
                        <input type="hidden" name="action"  value="<%=type%>" />
                        <div class="mb-3">
                            <label for="userID" class="form-label">User ID</label>
                            <input type="text" class="form-control" id="userID" name="userID" value="<%= id%>"/>
                        </div>
                        <div class="mb-3">
                            <label for="userName" class="form-label">User Name</label>
                            <input type="text" class="form-control" id="userName" name="userName" value="<%= name%>"/>
                        </div>
                        <div class="mb-3">
                            <label for="password" class="form-label">Password</label>
                            <input type="password" class="form-control" id="password" name="password" value="<%= password%>"/>
                        </div>
                        <div class="mb-3">
                            <label for="role" class="form-label">Role</label>
                            <select id="role" name="role" class="form-select">
                                <option <% if (role.equals("Student")) { %>selected<% } %> value="Student">Student</option>
                                <option <% if (role.equals("Technician")) { %>selected<% } %> value="Technician">Technician</option>
                                <option <% if (role.equals("Senior Technician")) { %>selected<% }%> value="Senior Technician">Senior Technician</option>
                            </select>
                        </div>
                        <input type="submit" class="btn btn-primary" />
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="footer.jsp" %>