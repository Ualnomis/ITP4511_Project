<%@page import="ict.bean.ReservationBean"%>
<%@page import="java.time.LocalDate"%>
<%@page import="ict.bean.EquipmentBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.UserBean"%>
<%@include file="header.jsp" %>
<% ArrayList<ReservationBean> reservations = (ArrayList<ReservationBean>) request.getAttribute("reservations"); %>
<div class="content">
    <div>
        <div class="page-title">
            <h3>Borrow Record
            </h3>
        </div>
        <div class="box box-primary">
            <div class="box-body">
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
                            <!--<th>Action</th>-->
                        </tr>
                    </thead>
                    <tbody>

                        <%
                            for (int i = 0; i < reservations.size(); i++) {
                                ReservationBean b = reservations.get(i);
                                if (b.getSubmitUserID() == USERID) {
                                    String modal = "disableModal" + b.getReservationID();
                                    out.println("<tr ");
                                    if ((LocalDate.now()).isAfter(b.getDueDate()) && ("Leasing".equalsIgnoreCase(b.getStatus()))) {
                                        out.println("style=\"color:white; background-color:red;\"");
                                    }
                                    out.println(">");
                                    out.println("<td>" + b.getReservationID() + "</td>");
                                    out.println("<td>" + b.getSubmitUserName() + " (ID: " + b.getSubmitUserID() + ")</td>");
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
//                                    out.println("<td>");
////                                  out.println("<a href=\"#" + modal + "\" class=\"btn btn-outline-danger btn-rounded\"><i class=\"fas fa-trash\"></i></a>");
////                                    out.println("<button type=\"button\" class=\"btn btn-outline-danger btn-rounded\" data-toggle=\"modal\" data-target=\"#" + modal + "\"><i class=\"fas fa-trash\">Request</i></button>");
//                                    out.println("/");
//                                    out.println("</td>");
                                    out.println("</tr>");
                                }

                        %>
                    <form method="post" action="equipmentBorrow" class="needs-validation" novalidate>
                        <div class="modal fade" id="<%= "disableModal" + b.getEquipmentID()%>" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" style="display: none;">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Borrow Equipment</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">×</span>
                                        </button>
                                    </div>

                                    <div class="modal-body text-left">
                                        <div class="form-group">
                                            <label>Qty:</label>
                                            <input class="form-control" name="qty" type="number" value="1" min="1" max="" step="1" />
                                        </div>
                                        <div class="form-group">
                                            <label>Start Date</label>
                                            <input class="form-control" name="startDate" type="date" value="<%= LocalDate.now()%>" min="<%= LocalDate.now()%>"/>
                                            <div class="invalid-feedback">
                                                Please input valid Start Date.
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label>End Date</label>
                                            <input class="form-control" name="endDate" type="date" value="<%= LocalDate.now()%>" min="<%= LocalDate.now()%>"/>
                                            <div class="invalid-feedback">
                                                Please input valid End Date.
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <input type="hidden" name="action" value="borrow" />
                                        <input type="hidden" name="equipmentID" value=<%= b.getEquipmentID()%> />
                                        <input type="hidden" name="userID" value=<%= user.getUserID()%> />
                                        <button type="submit" class="btn btn-primary">Confirm</button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <%

                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

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