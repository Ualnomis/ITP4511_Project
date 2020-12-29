<%@page import="java.time.LocalDate"%>
<%@page import="ict.bean.EquipmentBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.UserBean"%>
<%@include file="header.jsp" %>
<% ArrayList<EquipmentBean> equipments = (ArrayList<EquipmentBean>) request.getAttribute("equipments"); %>
<div class="content">
    <div>
        <div class="page-title">
            <h3>Equipment Borrow
            </h3>
        </div>
        <div class="box box-primary">
            <div class="box-body">
                <table width="100%" class="table table-hover" id="dataTables-example">
                    <thead>
                        <tr>
                            <th>Equipment ID</th>
                            <th>Equipment Name</th>
                            <th>Description</th>
                            <th>Stock</th>
                            <th>Status</th>
                            <th>Visibility</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>

                        <%
                            for (int i = 0; i < equipments.size(); i++) {
                                EquipmentBean b = equipments.get(i);
                                String modal = "disableModal" + b.getEquipmentID();
                                out.println("<tr>");
                                out.println("<td>" + b.getEquipmentID() + "</td>");
                                out.println("<td>" + b.getEquipmentName() + "</td>");
                                out.println("<td>" + b.getDescription() + "</td>");
                                out.println("<td>" + b.getStock() + "</td>");
                                out.println("<td>" + b.getStatus() + "</td>");
                                out.println("<td>");
                                if (b.isVisibility()) {
                                    out.println("Visible");
                                } else {
                                    out.println("Not visible");
                                }
                                out.println("</td>");
                                out.println("<td>");
//                              out.println("<a href=\"#" + modal + "\" class=\"btn btn-outline-danger btn-rounded\"><i class=\"fas fa-trash\"></i></a>");
                                out.println("<button type=\"button\" class=\"btn btn-outline-danger btn-rounded\" data-toggle=\"modal\" data-target=\"#" + modal + "\"><i class=\"fas fa-trash\">Request</i></button>");
                                out.println("</td>");
                                out.println("</tr>");

                        %>
                    <div class="modal fade" id="<%= "disableModal" + b.getEquipmentID()%>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" style="display: none;">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Borrow Equipment</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">×</span>
                                    </button>
                                </div>
                                <form method="post" action="equipmentBorrow">
                                    <div class="modal-body text-left">
                                        <label>Qty:</label>
                                        <input class="form-control" name="qty" type="number" value="1" min="1" max="<%= b.getStock() %>" step="1" />
                                               
                                            <label>Start Date</label>
                                        <input class="form-control" name="startDate" type="date" value="<%= LocalDate.now() %>" min="<%= LocalDate.now() %>"/>
                                        <label>End Date</label>
                                        <input class="form-control" name="endDate" type="date" value="<%= LocalDate.now() %>" min="<%= LocalDate.now() %>"/>
                                    </div>
                                    <div class="modal-footer">
                                        <input type="hidden" name="action" value="borrow" />
                                        <input type="hidden" name="equipmentID" value=<%= b.getEquipmentID()%> />
                                        <input type="hidden" name="userID" value=<%= user.getUserID()%> />
                                        <button type="submit" class="btn btn-primary">Confirm</button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                    </div>
                                </form>
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