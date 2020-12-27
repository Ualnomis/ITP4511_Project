<%@page import="java.util.ArrayList"%>
<%@page import="ict.bean.UserBean"%>
<%@include file="header.jsp" %>
<jsp:useBean id="editEquipment" class="ict.bean.EquipmentBean" scope="request"/>
<%
    String type = editEquipment.getEquipmentID() > 0 ? "edit" : "add";

    String id = editEquipment.getEquipmentID() > 0 ? editEquipment.getEquipmentID() + "" : "";

    String name = editEquipment.getEquipmentName() != null ? editEquipment.getEquipmentName() : "";

    String desc = editEquipment.getDescription() != null ? editEquipment.getDescription() : "";

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
                            <form accept-charset="utf-8" method="post" action="handleEquipment">
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
                                    <input type="text" class="form-control" id="eName" name="eName" value="<%= name%>"/>
                                </div>
                                <div class="form-group">
                                    <label for="eDesc" class="form-label">Equipment Description</label>
                                    <input type="text" class="form-control" id="eDesc" name="eDesc" value="<%= desc%>"/>
                                </div>
                                <div class="form-group">
                                    <label for="stock" class="form-label">Equipment Stock</label>
                                    <input type="number" min="0" step="1" class="form-control" id="stock" name="stock" value="<%= stock%>"/>
                                </div>
                                <div class="form-group">
                                    <label for="visibility" class="form-label">Equipment Visibility</label>
                                    <select id="visibility" name="visibility" class="form-control">
                                        <option <% if (isVisibility) { %> selected <% } %> value="true">Visible</option>
                                        <option <% if (!isVisibility) { %> selected <% }%> value="false">Not Visible</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <input type="submit" class="btn btn-primary" />
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="footer.jsp" %>