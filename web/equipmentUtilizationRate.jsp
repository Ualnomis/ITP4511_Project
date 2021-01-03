<%@page import="ict.bean.ReservationBean"%>
<%@page import="java.time.LocalDate"%>
<%@page import="ict.bean.EquipmentBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.UserBean"%>
<%@include file="header.jsp" %>
<%
    ArrayList<EquipmentBean> equipments = (ArrayList<EquipmentBean>) request.getAttribute("equipments");
    LocalDate startDate = (LocalDate) request.getAttribute("startDate");
    LocalDate endDate = (LocalDate) request.getAttribute("endDate");
%>
<div class="content">
    <div>
        <div class="page-title">
            <h3>
                Equipment Utilization Rate
            </h3>
        </div>
        <div class="box box-primary">
            <div class="box-body">
                <form method="post" action="analyticAndReport">
                    <input type="hidden" name="action" value="searchEquipmentUtilizationRate" />
                    Equipment ID: 
                    <input type="text" name="equipmentID" class="form-control" required required pattern="[0-9.]+" maxlength="9"  /> 
                    Start Date:
                    <input type="date" name="startDate" class="form-control" required />
                    End Date:
                    <input type="date" name="endDate" class="form-control" required />
                    <input type="submit" class="btn btn-primary" />
                </form>
                <hr />
                <table width="100%" class="table table-hover" id="dataTables-example">
                    <thead>
                        <tr>
                            <th>Equipment ID</th>
                            <th>Equipment Name</th>
                            <th>Utilization Rate</th>
                            <th>Period</th>
                        </tr>
                    </thead>
                    <tbody>

                        <%
                            for (int i = 0; i < equipments.size(); i++) {
                                EquipmentBean b = equipments.get(i);
                                if (b != null) {
                                    out.println("<tr ");
                                    out.println(">");
                                    out.println("<td>" + b.getEquipmentID() + "</td>");
                                    out.println("<td>" + b.getEquipmentName() + "</td>");
                                    out.println("<td>" + b.getUtilizationRate() + "%</td>");
                                    out.println("<td>");
                                    if (startDate != null && endDate != null) {
                                        out.println(startDate);
                                        out.println("-");
                                        out.println(endDate);
                                    } else {
                                        out.println("All Period");
                                    }
                                    out.println("</td>");
                                    out.println("</tr>");
                                }
                            }
                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>