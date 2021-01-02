<%@page import="ict.bean.ReservationBean"%>
<%@page import="java.time.LocalDate"%>
<%@page import="ict.bean.EquipmentBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.UserBean"%>
<%@page import="ict.db.AssignmentDB"%>
<%@include file="header.jsp" %>
<%
    ArrayList<ReservationBean> reservations = (ArrayList<ReservationBean>) request.getAttribute("reservations");
    ArrayList<ReservationBean> allReservations = (ArrayList<ReservationBean>) request.getAttribute("allReservations");
    ArrayList<Integer> newStudentsID = (ArrayList<Integer>) request.getAttribute("newStudentsID");
    
%>

<div class="content">
    <div>
        <div class="page-title">
            <h3>
                Selected Students Reservations
            </h3>
        </div>
        <div class="box box-primary">
            <div class="box-body">
                <form method="post" action="analyticAndReport">
                    <input type="hidden" name="action" value="searchStudentReservations" />
                    <div class="form-group">
                        Student UID: 
                        <div class="input-group">
                            <input type="text" name="student-number" class="form-control" /> 
                            <span class="input-group-append">
                                <!--                                <button type="button" class="btn btn-primary">Go!</button>-->
                                <input type="submit" class="btn btn-primary" />
                            </span>
                        </div>
                    </div>
                </form>

                Selected Students:
                <%
                    for (int i = 0; i < newStudentsID.size(); i++) {
                        out.println("<span class=\"badge badge-primary\">" + db.queryUserNameByID(newStudentsID.get(i))+ " (ID: " + newStudentsID.get(i) +")</span>");
                    }
                %>
                <br />
                <a href="analyticAndReport?action=listSelectedStudents" class="btn btn-primary">Reset</a>
                <hr />
                <table width="100%" class="table table-hover" id="dataTables-example">
                    <thead>
                        <tr>
                            <th>Reservation ID</th>
                            <th>Request User</th>
                            <th>Equipment Name</th>
                            <th>Qty</th>
                            <th>Request Date</th>
                            <th>Start Date</th>
                            <th>Due Date</th>
                            <th>Check-out Date</th>
                            <th>Check-in Date</th>
                            <th>Period</th>
                            <th>Status</th>
                            <th>Approve User</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>

                        <%
                            for (int i = 0; i < allReservations.size(); i++) {
                                ReservationBean b = allReservations.get(i);
                                out.println("<tr ");
                                if ((LocalDate.now()).isAfter(b.getDueDate()) && ("Leasing".equalsIgnoreCase(b.getStatus()))) {
                                    out.println("style=\"color:white; background-color:red;\"");
                                }
                                out.println(">");
                                out.println("<td>" + b.getReservationID() + "</td>");
                                out.println("<td>" + b.getSubmitUserName() + " (UID: " + b.getSubmitUserID() + ")</td>");
                                out.println("<td>" + b.getEquipmentName() + "</td>");
                                out.println("<td>" + b.getQty() + "</td>");
                                out.println("<td>");
                                if (b.getRequestDate() != null) {
                                    out.println(b.getRequestDate() + "");
                                } else {
                                    out.println("/");
                                }
                                out.println("</td>");
                                out.println("<td>");
                                if (b.getStartDate() != null) {
                                    out.println(b.getStartDate() + "");
                                } else {
                                    out.println("/");
                                }
                                out.println("</td>");
                                out.println("<td>");
                                if (b.getDueDate() != null) {
                                    out.println(b.getDueDate() + "");
                                } else {
                                    out.println("/");
                                }
                                out.println("</td>");
                                out.println("<td>");
                                if (b.getCheckOutDate() != null) {
                                    out.println(b.getCheckOutDate() + "");
                                } else {
                                    out.println("/");
                                }
                                out.println("</td>");
                                out.println("<td>");
                                if (b.getCheckInDate() != null) {
                                    out.println(b.getCheckInDate() + "");
                                } else {
                                    out.println("/");
                                }
                                out.println("</td>");
                                out.println("<td>" + b.getPeriod() + "</td>");
                                out.println("<td>" + b.getStatus() + "</td>");
                                out.println("<td>");
                                if (b.getApproveUserName() != null) {
                                    out.println(b.getApproveUserName() + "");
                                } else {
                                    out.println("/");
                                }
                                out.println("</td>");
                                out.println("<td>");
//                                  out.println("<a href=\"#" + modal + "\" class=\"btn btn-outline-danger btn-rounded\"><i class=\"fas fa-trash\"></i></a>");
//                                    out.println("<button type=\"button\" class=\"btn btn-outline-danger btn-rounded\" data-toggle=\"modal\" data-target=\"#" + modal + "\"><i class=\"fas fa-trash\">Request</i></button>");
                                out.println("/");
                                out.println("</td>");
                                out.println("</tr>");
                            }

                        %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<%@include file="footer.jsp" %>