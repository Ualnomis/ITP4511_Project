<%@page import="ict.bean.EquipmentBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.UserBean"%>
<%@include file="header.jsp" %>
<% ArrayList<EquipmentBean> equipments = (ArrayList<EquipmentBean>) request.getAttribute("equipments"); %>
<section id="account-management">
    <div class="container">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header ">
                    <div class="d-flex">
                        Equipment Management
                        <div class="ms-auto"><a href="editEquipment.jsp"><button class="btn btn-primary">Add Equipment</button></a></div>
                    </div>
                </div>
                <div class="card-body">
                    <table class="table table-hover">
                        <thead class="table-dark">
                        <th>Equipment ID</th>
                        <th>Equipment Name</th>
                        <th>Description</th>
                        <th>Stock</th>
                        <th>Status</th>
                        <th>visibility</th>
                        <th>Action</th>
                        </thead>
                        <tbody>
                            <%
                                for (int i = 0; i < equipments.size(); i++) {
                                    EquipmentBean b = equipments.get(i);
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
                                    out.println("<a href=\"handleEquipment?action=delete&id=" + b.getEquipmentID() + "\">delete</a>");
                                    out.println("<a href=\"handleEquipment?action=getEditEquipment&id="+ b.getEquipmentID() + "\">edit</a>");
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