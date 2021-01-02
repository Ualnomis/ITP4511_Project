<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/footerTag.tld" prefix="footerTag"%>
<%@ taglib uri="/WEB-INF/tlds/headerTag.tld" prefix="headerTag"%>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>IVPET Equipment</title>
        <link href="assets/vendor/fontawesome/css/fontawesome.min.css" rel="stylesheet">
        <link href="assets/vendor/fontawesome/css/solid.min.css" rel="stylesheet">
        <link href="assets/vendor/fontawesome/css/brands.min.css" rel="stylesheet">
        <link href="assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <link href="assets/vendor/datatables/datatables.min.css" rel="stylesheet">
        <link href="assets/vendor/chartsjs/Chart.min.css" rel="stylesheet">
        <link href="assets/vendor/flagiconcss/css/flag-icon.min.css" rel="stylesheet">
        <link href="assets/css/master.css" rel="stylesheet">
    </head>
    <jsp:useBean id="user" class="ict.bean.UserBean" scope="session" />
    <%
        final int USERID = user.getUserID();
    %>
    <body>
        <div class="wrapper">
            <!--left nav bar-->
            <nav id="sidebar">
                <div class="sidebar-header">
                    <img src="assets/img/logo_vtc.svg" alt="bootraper logo" class="app-logo" style="width: 210px; height: 42px;">
                </div>

                <% if ("Senior Technician".equals(user.getRole())) {%>
                <ul class="list-unstyled components text-secondary">
                    <li>
                        <a href="main.jsp"><i class="fas fa-home"></i> Dashboard</a>
                    </li>

                    <!--account management-->
                    <li>
                        <a href="#account-management" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle no-caret-down"><i class="fas fa-user"></i> Account Management</a>
                        <ul class="collapse list-unstyled" id="account-management">
                            <li>
                                <a href="handleUser?action=list"><i class="fas fa-angle-right"></i>List All User</a>
                            </li>
                            <li>
                                <a href="editUser.jsp"><i class="fas fa-angle-right"></i>Add User</a>
                            </li>
                        </ul>
                    </li>
                    <!--inventory management-->
                    <li>
                        <a href="#inventory-management" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle no-caret-down"><i class="fas fa-box"></i> Inventory Management</a>
                        <ul class="collapse list-unstyled" id="inventory-management">
                            <li>
                                <a href="handleEquipment?action=list"><i class="fas fa-angle-right"></i>List All Equipment</a>
                            </li>
                            <li>
                                <a href="editEquipment.jsp"><i class="fas fa-angle-right"></i>Add Equipment</a>
                            </li>
                        </ul>
                    </li>
                    <!--equipment borrowing-->
                    <li>
                        <a href="#equipment-borrowing" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle no-caret-down"><i class="fas fa-hand-holding"></i> Equipment Borrowing</a>
                        <ul class="collapse list-unstyled" id="equipment-borrowing">
                            <li>
                                <a href="equipmentBorrow?action=list"><i class="fas fa-angle-right"></i>List All Equipment</a>
                            </li>
                            <li>
                                <a href="equipmentBorrow?action=showBorrowRecord"><i class="fas fa-angle-right"></i>View Borrowing Record</a>
                            </li>
                            <li>
                                <a href="equipmentBorrow?action=borrowRequest"><i class="fas fa-angle-right"></i>Borrowing Request</a>
                            </li>
                            <li>
                                <a href="equipmentBorrow?action=showCheckOut"><i class="fas fa-angle-right"></i>Equipment Check-out</a>
                            </li>
                            <li>
                                <a href="equipmentBorrow?action=showCheckIn"><i class="fas fa-angle-right"></i>Equipment Check-in</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#analytic-report" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle no-caret-down"><i class="fas fa-chart-line"></i> Analytic/Report</a>
                        <ul class="collapse list-unstyled" id="analytic-report">
                            <li>
                                <a href="analyticAndReport?action=listSelectedStudents"><i class="fas fa-angle-right"></i>List Borrowing Records(Selected Students)</a>
                            </li>
                            <li>
                                <a href="analyticAndReport?action=listAllEquipmentUtilizationRate"><i class="fas fa-angle-right"></i>Equipments Utilization Rate</a>
                            </li>
                            <li>
                                <a href="analyticAndReport?action=listOverdue"><i class="fas fa-angle-right"></i>Overdue Reservations</a>
                            </li>
                        </ul>
                    </li>
                    <!--                    <li>
                                            <a href="settings.html"><i class="fas fa-cog"></i>Settings</a>
                                        </li>-->
                </ul>
                <% } else if ("Technician".equals(user.getRole())) { %>
                <ul class="list-unstyled components text-secondary">
                    <li>
                        <a href="main.jsp"><i class="fas fa-home"></i> Dashboard</a>
                    </li>
                    <!--inventory management-->
                    <li>
                        <a href="#inventory-management" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle no-caret-down"><i class="fas fa-box"></i> Inventory Management</a>
                        <ul class="collapse list-unstyled" id="inventory-management">
                            <li>
                                <a href="handleEquipment?action=list"><i class="fas fa-angle-right"></i>List All Equipment</a>
                            </li>
                            <li>
                                <a href="editEquipment.jsp"><i class="fas fa-angle-right"></i>Add Equipment</a>
                            </li>
                        </ul>
                    </li>
                    <!--equipment borrowing-->
                    <li>
                        <a href="#equipment-borrowing" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle no-caret-down"><i class="fas fa-layer-group"></i> Equipment Borrowing</a>
                        <ul class="collapse list-unstyled" id="equipment-borrowing">
                            <li>
                                <a href="equipmentBorrow?action=list"><i class="fas fa-angle-right"></i>List All Equipment</a>
                            </li>
                            <li>
                                <a href="equipmentBorrow?action=showBorrowRecord"><i class="fas fa-angle-right"></i>View Borrowing Record</a>
                            </li>
                            <li>
                                <a href="equipmentBorrow?action=borrowRequest"><i class="fas fa-angle-right"></i>Borrowing Request</a>
                            </li>
                            <li>
                                <a href="equipmentBorrow?action=showCheckOut"><i class="fas fa-angle-right"></i>Equipment Check-out</a>
                            </li>
                            <li>
                                <a href="equipmentBorrow?action=showCheckIn"><i class="fas fa-angle-right"></i>Equipment Check-in</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="#analytic-report" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle no-caret-down"><i class="fas fa-chart-line"></i> Analytic/Report</a>
                        <ul class="collapse list-unstyled" id="analytic-report">
                            <li>
                                <a href="analyticAndReport?action=listSelectedStudents"><i class="fas fa-angle-right"></i>List Borrowing Records(Selected Students)</a>
                            </li>
                            <li>
                                <a href="analyticAndReport?action=listOverdue"><i class="fas fa-angle-right"></i>Overdue Reservations</a>
                            </li>
                        </ul>
                    </li>
                    <!--                    <li>
                                            <a href="settings.html"><i class="fas fa-cog"></i>Settings</a>
                                        </li>-->
                </ul>
                <% } else if ("Student".equals(user.getRole())) { %>
                <ul class="list-unstyled components text-secondary">
                    <li>
                        <a href="main.jsp"><i class="fas fa-home"></i> Dashboard</a>
                    </li>
                    <!--equipment borrowing-->
                    <li>
                        <a href="#equipment-borrowing" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle no-caret-down"><i class="fas fa-layer-group"></i> Equipment Borrowing</a>
                        <ul class="collapse list-unstyled" id="equipment-borrowing">
                            <li>
                                <a href="equipmentBorrow?action=list"><i class="fas fa-angle-right"></i>List All Equipment</a>
                            </li>
                            <li>
                                <a href="equipmentBorrow?action=showBorrowRecord"><i class="fas fa-angle-right"></i>View Borrowing Record</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="settings.html"><i class="fas fa-cog"></i>Settings</a>
                    </li>
                </ul>
                <% }%>
            </nav>
            <!--top nav bar-->
            <div id="body" class="active">
                <nav class="navbar navbar-expand-lg navbar-white bg-white">
                    <button type="button" id="sidebarCollapse" class="btn btn-light"><i class="fas fa-bars"></i><span></span></button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="nav navbar-nav ml-auto">
                            <li class="nav-item dropdown">
                                <div class="nav-dropdown">
                                    <a href="" class="nav-item nav-link dropdown-toggle text-secondary" data-toggle="dropdown"><i class="fas fa-user"></i> <span><%= user.getName()%></span> <i style="font-size: .8em;" class="fas fa-caret-down"></i></a>
                                    <div class="dropdown-menu dropdown-menu-right nav-link-menu">
                                        <ul class="nav-list">
                                            <!--                                            <li><a href="" class="dropdown-item"><i class="fas fa-address-card"></i> Profile</a></li>
                                                                                        <li><a href="" class="dropdown-item"><i class="fas fa-envelope"></i> Messages</a></li>
                                                                                        <li><a href="" class="dropdown-item"><i class="fas fa-cog"></i> Settings</a></li>-->
                                            <div class="dropdown-divider"></div>
                                            <li>
                                                <form id="logout" method="post" action="login">
                                                    <input type="hidden" name="action" value="logout" />
                                                    <a href="javascript:;" onclick="document.getElementById('logout').submit();" class="dropdown-item"><i class="fas fa-sign-out-alt"></i> Logout</a>
                                                </form>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </nav>
