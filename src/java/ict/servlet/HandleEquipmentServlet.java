/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.Servlet;

import ict.bean.EquipmentBean;
import ict.bean.UserBean;
import ict.db.AssignmentDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 2689
 */
@WebServlet(name = "HandleEquipmentServlet", urlPatterns = {"/handleEquipment"})
public class HandleEquipmentServlet extends HttpServlet {

    private AssignmentDB db;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public void init() {
        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        db = new AssignmentDB(dbUrl, dbUser, dbPassword);

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");

        if ("list".equalsIgnoreCase(action)) {
            // call the query db to get retrieve for all customer 
            ArrayList<EquipmentBean> equipments = db.queryAllEquipment();
            // set the result into the attribute	 
            request.setAttribute("equipments", equipments);
            // redirect the result to the listCustomers.jsp
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/equipmentManagement.jsp");
            rd.forward(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            // get parameter, id, from the request
            if (request.getParameter("id") != null) {
                // call delete record method in the database
                db.delEquipmentRecord(Integer.parseInt(request.getParameter("id")));
                // redirect the result to list action 
                response.sendRedirect("handleEquipment?action=list");
            }
        } else if (action.equalsIgnoreCase("add")) {
            int stock = Integer.parseInt(request.getParameter("stock"));
            String status = "unavailable";
            if (stock > 0) {
                status = "available";
            }
            
            if (db.addEquipmentRecord(request.getParameter("eName"), status, request.getParameter("eDesc"), stock, Boolean.parseBoolean(request.getParameter("visibility")))) {
                response.sendRedirect("handleEquipment?action=list");
            } else {
                response.sendRedirect("editEquipment.jsp?error=true");
            }
        } else if ("getEditEquipment".equalsIgnoreCase(action)) {
            if (request.getParameter("id") != null) {
                // call query db to get retrieve for a customer with the given id
                EquipmentBean bean = db.queryEquipmentByID(Integer.parseInt(request.getParameter("id")));
                // set the customer as attribute in request scope
                request.setAttribute("editEquipment", bean);

                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/editEquipment.jsp");
                rd.forward(request, response);
            }
        } else if ("edit".equalsIgnoreCase(action)) {
            String eName = request.getParameter("eName")  != null ? request.getParameter("eName") : "";
            String eDesc = request.getParameter("eDesc") != null ? request.getParameter("eDesc") : "";
            int stock = Integer.parseInt(request.getParameter("stock"));
            int id = Integer.parseInt(request.getParameter("id"));
            boolean visibility = Boolean.parseBoolean(request.getParameter("visibility"));
            String status = "unavailable";
            if (stock > 0) {
                status = "available";
            }
            // call  editCustomer to update the database record
            EquipmentBean bean = new EquipmentBean();
            bean.setEquipmentID(id);
            bean.setEquipmentName(eName);
            bean.setDescription(eDesc);
            bean.setStatus(status);
            bean.setStock(stock);
            bean.setVisibility(visibility);
            if (db.editEquipmentRecord(bean)) {
                response.sendRedirect("handleEquipment?action=list");
            } else {
                response.sendRedirect("loginError.jsp?" + eDesc);
            }
            
        } else {
            PrintWriter out = response.getWriter();
            out.println("No such action!!!");
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
