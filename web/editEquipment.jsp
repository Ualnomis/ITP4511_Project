<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.UserBean"%>
<%@include file="header.jsp" %>
<jsp:useBean id="editEquipment" class="ict.bean.EquipmentBean" scope="request"/>
<%
    String type = editEquipment.getEquipmentID() > 0 ? "edit" : "add";

    String id = editEquipment.getEquipmentID() > 0 ? editEquipment.getEquipmentID() + "" : "";

    String name = editEquipment.getEquipmentName() != null ? editEquipment.getEquipmentName() : "";

    String desc = editEquipment.getDescription() != null ? editEquipment.getDescription() : "";

    String status = editEquipment.getStatus() == null ? "available" : editEquipment.getStatus();
    
    boolean isVisibility = editEquipment.isVisibility();

    if ("add".equals(type)) {
        isVisibility = true;
    }

    int stock = editEquipment.getStock();
%>
<section id="edit-user">
    <div class="content">
        <div class="container">
            <div class="page-title">
                <h3>Equipment Management</h3>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="card">
                        <div class="card-header">
                            <% if (type.equals("edit")) { %>
                            Edit Equipment
                            <% } else { %>
                            Add Equipment
                            <% }%>
                        </div>
                        <div class="card-body">
                            <h5 class="card-title"></h5>
                            <form accept-charset="utf-8" method="post" action="handleEquipment" class="needs-validation" novalidate>
                                <input type="hidden" name="action"  value="<%=type%>" />
                                <input type="hidden" name="id"  value="<%=id%>" />
                                <%
                                    if ("edit".equals(type)) {
                                %>
                                <div class="form-group">
                                    <label for="eID" class="form-label">Equipment ID</label>
                                    <input type="text" class="form-control" id="eName" value="<%= id%>" disabled=""/>
                                </div>
                                <% }%>
                                <div class="form-group">
                                    <label for="eName" class="form-label">Equipment Name</label>
                                    <input type="text" class="form-control" id="eName" name="eName" value="<%= name%>" required />
                                    <div class="invalid-feedback">
                                        Please input valid equipment name.
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="eDesc" class="form-label">Equipment Description</label>
                                    <input type="text" class="form-control" id="eDesc" name="eDesc" value="<%= desc%>" required />
                                    <div class="invalid-feedback">
                                        Please input valid equipment description.
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="stock" class="form-label">Equipment Stock</label>
                                    <input type="number" min="0" step="1" class="form-control" id="stock" name="stock" value="<%= stock%>"/>
                                    <div class="invalid-feedback">
                                        Please input valid equipment stock.
                                    </div>
                                </div>
<!--                                <div class="form-group">
                                    <label for="status" class="form-label">Equipment Status</label>
                                    <select id="status" name="status" class="form-control">
                                        <option <% if ("available".equals(status)) { %> selected <% } %> value="available">available</option>
                                        <option <% if ("unavailable".equals(status)) { %> selected <% }%> value="unavailable">unavailable</option>
                                    </select>
                                    <div class="invalid-feedback">
                                        Please selected Equipment Status.
                                    </div>
                                </div>-->
                                <div class="form-group">
                                    <label for="visibility" class="form-label">Equipment Visibility</label>
                                    <select id="visibility" name="visibility" class="form-control">
                                        <option <% if (isVisibility) { %> selected <% } %> value="true">Visible</option>
                                        <option <% if (!isVisibility) { %> selected <% }%> value="false">Not Visible</option>
                                    </select>
                                    <div class="invalid-feedback">
                                        Please selected Equipment Visibility.
                                    </div>
                                </div>
                                <div class="form-group">
                                    <input type="submit" value="value="<%= ("add".equals(type)) ? "Add" : "Edit" %>"" class="btn btn-primary" />
                                    <a href="handleEquipment?action=list" class="btn btn-danger">Cancel</a>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
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