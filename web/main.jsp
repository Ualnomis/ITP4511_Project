<%@page import="ict.bean.ReservationBean"%>
<%@page import="ict.bean.EquipmentBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ict.db.AssignmentDB"%>
<%@include file="header.jsp" %>
<%!
    public AssignmentDB db = new AssignmentDB("jdbc:mysql://localhost:3306/itp4511_db", "root", "");

%>
<div class="content">
    <div class="container">
        <div class="row">
            <div class="col-md-12 page-header">
                <div class="page-pretitle">Overview</div>
                <h2 class="page-title">Dashboard</h2>
            </div>
        </div>
        <% if ("Student".equals(user.getRole())) {%>
        <!--tatol borrow, overdue item, Approved item, leasing item-->
        <div class="row">
            <div class="col-sm-6 col-md-6 col-lg-3 mt-3">
                <div class="card">
                    <div class="content">
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="icon-big text-center">
                                    <i class="teal fas fas fa-hand-holding"></i>
                                </div>
                            </div>
                            <div class="col-sm-8">
                                <div class="detail">
                                    <p class="detail-subtitle">Total Reservations</p>
                                    <span class="number"><%= db.countAllReservationsByUserID(USERID)%></span>
                                </div>
                            </div>
                        </div>
                        <div class="footer">
                            <br />
                            <div class="stats">
                                <!--                                <i class="fas fa-calendar"></i> For this Week-->
                                <br />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-6 col-lg-3 mt-3">
                <div class="card">
                    <div class="content">
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="icon-big text-center">
                                    <i class="violet fas fa-hand-holding"></i>
                                </div>
                            </div>
                            <div class="col-sm-8">
                                <div class="detail">
                                    <p class="detail-subtitle">Approved</p>
                                    <span class="number"><%= db.countAllApprovedReservationsByUserID(USERID)%></span>
                                </div>
                            </div>
                        </div>
                        <div class="footer">
                            <br />
                            <div class="stats">
                                <!--<i class="fas fa-stopwatch"></i>For this Month-->
                                <br />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-6 col-lg-3 mt-3">
                <div class="card">
                    <div class="content">
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="icon-big text-center">
                                    <i class="orange fas fa-hand-holding"></i>
                                </div>
                            </div>
                            <div class="col-sm-8">
                                <div class="detail">
                                    <p class="detail-subtitle">Leasing</p>
                                    <span class="number"><%= db.countAllLeasingReservationsByUserID(USERID)%></span>
                                </div>
                            </div>
                        </div>
                        <div class="footer">
                            <br />
                            <div class="stats">
                                <!--<i class="fas fa-envelope-open-text"></i> For this week-->
                                <br />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-6 col-lg-3 mt-3">
                <div class="card">
                    <div class="content">
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="icon-big text-center">
                                    <i class="fas fa-hand-holding" style="color: red;"></i>
                                </div>
                            </div>
                            <div class="col-sm-8">
                                <div class="detail">
                                    <p class="detail-subtitle" style="color: red;">Overdue</p>
                                    <span class="number" style="color: red;"><%= db.countAllOverdueReservationsByUserID(USERID)%></span>
                                </div>
                            </div>
                        </div>
                        <div class="footer">
                            <br />
                            <div class="stats">
                                <!--                                <i class="fas fa-calendar"></i> For this Month-->
                                <br />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
            </div>
            <!--Top borrow equipment-->
            <div class="col-md-6">
                <div class="card">
                    <div class="content">
                        <div class="head">
                            <h5 class="mb-0">Top borrow equipment</h5>
                            <p class="text-muted">Overall website borrow data</p>
                        </div>
                        <div class="canvas-wrapper">
                            <table class="table no-margin bg-lighter-grey">
                                <thead class="success">
                                    <tr>
                                        <th>Equipment ID</th>
                                        <th>Equipment Name</th>
                                        <th class="text-right">Borrow count</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        ArrayList<EquipmentBean> equipments = db.queryCountEquipmentBorrow();
                                    %>
                                    <% for (int i = 0; i < equipments.size(); i++) {%>
                                    <tr>
                                        <td><%= equipments.get(i).getEquipmentID()%></td>
                                        <td><%= equipments.get(i).getEquipmentName()%></td>
                                        <td class="text-right"><%= equipments.get(i).getBorrowCount()%></td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <% } else {%>
        <!--tatol borrow, Request, leasing item, overdue item-->
        <div class="row">
            <div class="col-sm-6 col-md-6 col-lg-3 mt-3">
                <div class="card">
                    <div class="content">
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="icon-big text-center">
                                    <i class="teal fas fa-hand-holding"></i>
                                </div>
                            </div>
                            <div class="col-sm-8">
                                <div class="detail">
                                    <p class="detail-subtitle">Total Reservations</p>
                                    <span class="number"><%= db.countAllReservations()%></span>
                                </div>
                            </div>
                        </div>
                        <div class="footer">
                            <br />
                            <div class="stats">
                                <!-- <i class="fas fa-calendar"></i> For this Week-->
                                <br />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-6 col-lg-3 mt-3">
                <div class="card">
                    <div class="content">
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="icon-big text-center">
                                    <i class="olive fas fa-hand-holding"></i>
                                </div>
                            </div>
                            <div class="col-sm-8">
                                <div class="detail">
                                    <p class="detail-subtitle">Request</p>
                                    <span class="number"><%= db.countAllRequestingReservations()%></span>
                                </div>
                            </div>
                        </div>
                        <div class="footer">
                            <br />
                            <div class="stats">
                                <!--                                <i class="fas fa-calendar"></i> For this Month-->
                                <br />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-6 col-lg-3 mt-3">
                <div class="card">
                    <div class="content">
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="icon-big text-center">
                                    <i class="violet fas fa-hand-holding"></i>
                                </div>
                            </div>
                            <div class="col-sm-8">
                                <div class="detail">
                                    <p class="detail-subtitle">Leasing</p>
                                    <span class="number"><%= db.countAllLeasingReservations()%></span>
                                </div>
                            </div>
                        </div>
                        <div class="footer">
                            <br />
                            <div class="stats">
                                <!--<i class="fas fa-stopwatch"></i>For this Month-->
                                <br />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-6 col-md-6 col-lg-3 mt-3">
                <div class="card">
                    <div class="content">
                        <div class="row">
                            <div class="col-sm-4">
                                <div class="icon-big text-center">
                                    <i class="fas fa-hand-holding" style="color: red;"></i>
                                </div>
                            </div>
                            <div class="col-sm-8">
                                <div class="detail">
                                    <p class="detail-subtitle" style="color: red;">Overdue</p>
                                    <span class="number" style="color: red;"><%= db.countAllOverdueReservations()%></span>
                                </div>
                            </div>
                        </div>
                        <div class="footer">
                            <br />
                            <div class="stats">
                                <!--<i class="fas fa-envelope-open-text"></i> For this week-->
                                <br />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-12">
            </div>
            <!--Top borrow equipment-->
            <div class="col-md-6">
                <div class="card">
                    <div class="content">
                        <div class="head">
                            <h5 class="mb-0">Top borrow equipment</h5>
                            <p class="text-muted">Overall website borrow data</p>
                        </div>
                        <div class="canvas-wrapper">
                            <table class="table no-margin bg-lighter-grey">
                                <thead class="success">
                                    <tr>
                                        <th>Equipment ID</th>
                                        <th>Equipment Name</th>
                                        <th class="text-right">Borrow count</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        ArrayList<EquipmentBean> equipments = db.queryCountEquipmentBorrow();
                                    %>
                                    <% for (int i = 0; i < equipments.size(); i++) {%>
                                    <tr>
                                        <td><%= equipments.get(i).getEquipmentID()%></td>
                                        <td><%= equipments.get(i).getEquipmentName()%></td>
                                        <td class="text-right"><%= equipments.get(i).getBorrowCount()%></td>
                                    </tr>
                                    <% } %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <% }%>
    </div>
</div>
<%@include file="footer.jsp" %>