<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.UserBean"%>
<%@include file="header.jsp" %>
<% ArrayList<UserBean> users = (ArrayList<UserBean>) request.getAttribute("users"); %>
<div class="content">
    <div class="">
        <div class="page-title">
            <h3>Account Management
                <a href="roles.html" class="btn btn-sm btn-outline-primary float-right"><i class="fas fa-user-shield"></i> Add User</a>
            </h3>
        </div>
        <div class="box box-primary">
            <div class="box-body">
                <table width="100%" class="table table-hover" id="dataTables-example">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Email</th>
                            <th>Name</th>
                            <th>Gender</th>
                            <th>Phone</th>
                            <th>Role</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>

                        <%
                            for (int i = 0; i < users.size(); i++) {
                                UserBean ub = users.get(i);
                                String modal = "disableModal" + ub.getUserID();
                                out.println("<tr>");
                                out.println("<td>" + ub.getUserID() + "</td>");
                                out.println("<td>" + ub.getEmail() + "</td>");
                                out.println("<td>" + ub.getName() + "</td>");
                                out.println("<td>" + ub.getGender() + "</td>");
                                out.println("<td>" + ub.getPhone() + "</td>");
                                out.println("<td>" + ub.getRole() + "</td>");
                                out.println("<td>" + (ub.isStatus() ? "Active" : "Disable") + "</td>");
                                out.println("<td>");
                                out.println("<a href=\"handleUser?action=getEditUser&id=" + ub.getUserID() + "\" class=\"btn btn-outline-info btn-rounded\"><i class=\"fas fa-pen\"></i></a>");
//                                out.println("<a href=\"#" + modal + "\" class=\"btn btn-outline-danger btn-rounded\"><i class=\"fas fa-trash\"></i></a>");
                                out.println("<button type=\"button\" class=\"btn btn-outline-danger btn-rounded\" data-toggle=\"modal\" data-target=\"#" + modal + "\"><i class=\"fas fa-trash\"></i></button>");
                                out.println("</td>");
                                out.println("</tr>");

                        %>
                    <div class="modal fade" id="<%= "disableModal" + ub.getUserID()%>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" style="display: none;">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Disable User</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">×</span>
                                    </button>
                                </div>
                                <div class="modal-body text-left">
                                    Are you sure disable this account?
                                </div>
                                <div class="modal-footer">
                                    <form method="get" action="handleUser">
                                        <input type="hidden" name="action" value="delete" />
                                        <input type="hidden" name="id" value=<%= ub.getUserID() %> />
                                        <button type="submit" class="btn btn-danger">Disable</button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <% }%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>


</div>
<%@include file="footer.jsp" %>