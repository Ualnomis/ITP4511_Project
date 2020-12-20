<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.UserBean"%>
<%@include file="header.jsp" %>
<% ArrayList<UserBean> users = (ArrayList<UserBean>) request.getAttribute("users"); %>
<section id="account-management">
    <div class="container">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header ">
                    <div class="d-flex">
                        Account Management
                        <div class="ms-auto"><a href="editUser.jsp"><button class="btn btn-primary">Add User</button></a></div>
                    </div>
                </div>
                <div class="card-body">
                    <table class="table table-hover">
                        <thead class="table-dark">
                        <th>User ID</th>
                        <th>User Name</th>
                        <th>Password</th>
                        <th>Role</th>
                        <th>Action</th>
                        </thead>
                        <tbody>
                            <%
                                for (int i = 0; i < users.size(); i++) {
                                    UserBean ub = users.get(i);
                                    out.println("<tr>");
                                    out.println("<td>" + ub.getUserID() + "</td>");
                                    out.println("<td>" + ub.getName() + "</td>");
                                    out.println("<td>" + ub.getPw() + "</td>");
                                    out.println("<td>" + ub.getRole() + "</td>");
                                    out.println("<td>");
                                    out.println("<a href=\"handleUser?action=delete&id=" + ub.getUserID() + "\">delete</a>");
                                    out.println("<a href=\"handleUser?action=getEditUser&id="+ ub.getUserID() + "\">edit</a>");
                                    out.println("</td>");
                                    out.println("</tr>");
                                }
                            %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
</section>
<%@include file="footer.jsp" %>