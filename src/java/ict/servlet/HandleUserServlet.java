/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.Servlet;

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
@WebServlet(name = "HandleUserServlet", urlPatterns = {"/handleUser"})
public class HandleUserServlet extends HttpServlet {

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
            ArrayList<UserBean> users = db.queryAllUser();
            // set the result into the attribute	 
            request.setAttribute("users", users);
            // redirect the result to the listCustomers.jsp
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/accountManagement.jsp");
            rd.forward(request, response);
        } else if ("delete".equalsIgnoreCase(action)) {
            // get parameter, id, from the request
            if (request.getParameter("id") != null) {
                // call delete record method in the database
                db.disableUser(Integer.parseInt(request.getParameter("id")));
                // redirect the result to list action 
                response.sendRedirect("handleUser?action=list");
            }
        } else if ("getEditUser".equalsIgnoreCase(action)) {
            if (request.getParameter("id") != null) {
                // call query db to get retrieve for a customer with the given id
                UserBean ub = db.queryUserByID(Integer.parseInt(request.getParameter("id")));
                // set the customer as attribute in request scope
                request.setAttribute("editUser", ub);

                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/editUser.jsp");
                rd.forward(request, response);
            }
        } else if (action.equalsIgnoreCase("add")) {
            if (db.addUserRecord(request.getParameter("email"), request.getParameter("name"), request.getParameter("password"), request.getParameter("gender"), request.getParameter("phone"), request.getParameter("role"), Boolean.parseBoolean(request.getParameter("status")))) {
                response.sendRedirect("handleUser?action=list");
            } else {
                response.sendRedirect("editUser.jsp?error=true");
            }
        } else if (action.equalsIgnoreCase("edit")) {
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String role = request.getParameter("role");
            int id = Integer.parseInt(request.getParameter("id"));
            String gender = request.getParameter("gender");
            String phone = request.getParameter("phone");
            String email = request.getParameter("email");
            boolean status = Boolean.parseBoolean(request.getParameter("status"));

            // call  editCustomer to update the database record
            UserBean ub = new UserBean();
            ub.setPassword(password);
            ub.setUserID(id);
            ub.setName(name);
            ub.setRole(role);
            ub.setGender(gender);
            ub.setPhone(phone);
            ub.setStatus(status);
            ub.setEmail(email);
            db.editUserRecord(ub);
            // redirect the result to “list” action again
            response.sendRedirect("handleUser?action=list");
        } else if ("unlock".equalsIgnoreCase(action)) {
            // get parameter, id, from the request
            if (request.getParameter("id") != null) {
                // call delete record method in the database
                db.enableUser(Integer.parseInt(request.getParameter("id")));
                // redirect the result to list action 
                response.sendRedirect("handleUser?action=list");
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
