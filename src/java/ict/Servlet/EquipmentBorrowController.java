/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.Servlet;

import ict.bean.EquipmentBean;
import ict.bean.ReservationBean;
import ict.db.AssignmentDB;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
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
@WebServlet(name = "EquipmentBorrowController", urlPatterns = {"/equipmentBorrow"})
public class EquipmentBorrowController extends HttpServlet {

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
        String action = request.getParameter("action");

        if ("list".equalsIgnoreCase(action)) {
            // call the query db to get retrieve for all customer 
            ArrayList<EquipmentBean> equipments = db.queryAllEquipmentBorrowable();
            // set the result into the attribute	 
            request.setAttribute("equipments", equipments);
            // redirect the result to the listCustomers.jsp
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/borrowEquipment.jsp");
            rd.forward(request, response);
        } else if ("borrow".equalsIgnoreCase(action)) {
            int submitUserID = Integer.parseInt(request.getParameter("userID"));
            int equipmentID = Integer.parseInt(request.getParameter("equipmentID"));
            int qty = Integer.parseInt(request.getParameter("qty"));
            LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
            LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
            Period period = Period.between(startDate, endDate);
            int periodDays = period.getDays();
            periodDays = (periodDays == 0) ? 1 : periodDays;
            db.addReservationRecord(submitUserID, equipmentID, qty, LocalDate.now(), startDate, endDate, null, null, periodDays, 0, "Requesting");
            db.reduceEquipmentQtyByID(equipmentID, qty);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/equipmentBorrow?action=showBorrowRecord");
            rd.forward(request, response);
        } else if ("showBorrowRecord".equalsIgnoreCase(action)) {
            // call the query db to get retrieve for all customer 
            ArrayList<ReservationBean> reservations = db.queryAllReservation();
            // set the result into the attribute	 
            request.setAttribute("reservations", reservations);
            // redirect the result to the borrowRequest.jsp
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/borrowRecord.jsp");
            rd.forward(request, response);
        } else if ("borrowRequest".equalsIgnoreCase(action)) {
            // call the query db to get retrieve for all customer 
            ArrayList<ReservationBean> reservations = db.queryRequestReservation();
            // set the result into the attribute	 
            request.setAttribute("reservations", reservations);
            // redirect the result to the borrowRequest.jsp
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/borrowRequest.jsp");
            rd.forward(request, response);
        } else if ("acceptRequest".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("reservationID"));
            int approveUserID = Integer.parseInt(request.getParameter("approvedUserID"));
            int qty = Integer.parseInt(request.getParameter("qty"));
            int equipmentID = Integer.parseInt(request.getParameter("equipmentID"));
            if (db.editReservationStatusByID(id, "Approved", approveUserID, equipmentID, qty)) {
                // redirect the result to the listCustomers.jsp
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/equipmentBorrow?action=borrowRequest");
                rd.forward(request, response);
            }
        } else if ("rejectRequest".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("reservationID"));
            int approveUserID = Integer.parseInt(request.getParameter("approvedUserID"));
            int qty = Integer.parseInt(request.getParameter("qty"));
            int equipmentID = Integer.parseInt(request.getParameter("equipmentID"));
            if (db.editReservationStatusByID(id, "Rejected", approveUserID, equipmentID, qty)) {
                // redirect the result to the listCustomers.jsp
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/equipmentBorrow?action=borrowRequest");
                rd.forward(request, response);
            }
        } else if ("checkOut".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("reservationID"));
            if (db.checkOutByID(id)) {
                // redirect the result to the listCustomers.jsp
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/equipmentBorrow?action=showCheckOut");
                rd.forward(request, response);
            }
        } else if ("showCheckOut".equalsIgnoreCase(action)) {
            // call the query db to get retrieve for all customer 
            ArrayList<ReservationBean> reservations = db.queryApprovedReservation();
            // set the result into the attribute	 
            request.setAttribute("reservations", reservations);
            // redirect the result to the borrowRequest.jsp
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/equipmentCheckOut.jsp");
            rd.forward(request, response);
        } else if ("checkIn".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("reservationID"));
            int qty = Integer.parseInt(request.getParameter("qty"));
            int equipmentID = Integer.parseInt(request.getParameter("equipmentID"));
            if (db.checkInByID(id, equipmentID, qty)) {
                // redirect the result to the listCustomers.jsp
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/equipmentBorrow?action=showCheckIn");
                rd.forward(request, response);
            }
        } else if ("showCheckIn".equalsIgnoreCase(action)) {
            // call the query db to get retrieve for all customer 
            ArrayList<ReservationBean> reservations = db.queryLeasingReservation();
            // set the result into the attribute	 
            request.setAttribute("reservations", reservations);
            // redirect the result to the borrowRequest.jsp
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/equipmentCheckIn.jsp");
            rd.forward(request, response);
        }  else if ("checkoutOvderdue".equalsIgnoreCase(action)) {
            int id = Integer.parseInt(request.getParameter("reservationID"));
            int qty = Integer.parseInt(request.getParameter("qty"));
            int equipmentID = Integer.parseInt(request.getParameter("equipmentID"));
            if (db.checkInByID(id, equipmentID, qty)) {
                // redirect the result to the listCustomers.jsp
                RequestDispatcher rd;
                rd = getServletContext().getRequestDispatcher("/equipmentBorrow?action=showCheckOut");
                rd.forward(request, response);
            }
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
