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
    <div class="container">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <div class="d-flex">
                        <% if (type.equals("edit")) { %>
                        Edit Equipment
                        <% } else { %>
                        Add Equipment
                        <% }%>
                    </div>
                </div>
                <div class="card-body">
                    <form method="post" action="handleEquipment">
                        <input type="hidden" name="action"  value="<%=type%>" />
                        <input type="hidden" name="id"  value="<%=id%>" />
                        <div class="mb-3">
                            <label for="eName" class="form-label">Equipment Name</label>
                            <input type="text" class="form-control" id="eName" name="eName" value="<%= name%>"/>
                        </div>
                        <div class="mb-3">
                            <label for="eDesc" class="form-label">Equipment Description</label>
                            <input type="text" class="form-control" id="eDesc" name="eDesc" value="<%= desc%>"/>
                        </div>
                        <div class="mb-3">
                            <label for="stock" class="form-label">Equipment Stock</label>
                            <input type="number" class="form-control" id="stock" name="stock" value="<%= stock%>"/>
                        </div>
                        <div class="mb-3">
                            <label for="visibility" class="form-label">Equipment Visibility</label>
                            <select id="visibility" name="visibility" class="form-select">
                                <option <% if (isVisibility) { %> selected <% } %> value="true">Visible</option>
                                <option <% if (!isVisibility) { %> selected <% } %> value="false">Not Visible</option>
                            </select>
                        </div>
                        <input type="submit" class="btn btn-primary" />
                    </form>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="footer.jsp" %>