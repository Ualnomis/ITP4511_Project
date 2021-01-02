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
@WebServlet(name = "AnalyticAndReportServlet", urlPatterns = {"/analyticAndReport"})
public class AnalyticAndReportServlet extends HttpServlet {

    private AssignmentDB db;
    ArrayList<ReservationBean> allReservations = new ArrayList<ReservationBean>();
    ArrayList<Integer> studentsID = new ArrayList<Integer>();
    ArrayList<EquipmentBean> allEquipments = new ArrayList<EquipmentBean>();
    ArrayList<Integer> newStudentsID = new ArrayList<Integer>();

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

        if ("listOverdue".equalsIgnoreCase(action)) {
            // call the query db to get retrieve for all customer 
            ArrayList<ReservationBean> reservations = db.queryLeasingReservation();
            // set the result into the attribute	 
            request.setAttribute("reservations", reservations);
            // redirect the result to the borrowRequest.jsp
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/overdueReservation.jsp");
            rd.forward(request, response);
        } else if ("listSelectedStudents".equalsIgnoreCase(action)) {
            // call the query db to get retrieve for all customer 
            ArrayList<ReservationBean> reservations = new ArrayList<ReservationBean>();
            allReservations = new ArrayList<ReservationBean>();
            studentsID = new ArrayList<Integer>();
            newStudentsID = new ArrayList<Integer>();
            // set the result into the attribute	 
            request.setAttribute("reservations", reservations);
            request.setAttribute("allReservations", allReservations);
            request.setAttribute("newStudentsID", newStudentsID);
            // redirect the result to the borrowRequest.jsp
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/selectedStudentsReservations.jsp");
            rd.forward(request, response);
        } else if ("searchStudentReservations".equalsIgnoreCase(action)) {
            int stuID = Integer.parseInt(request.getParameter("student-number"));
            ArrayList<ReservationBean> reservations = new ArrayList<ReservationBean>();
            for (int i = 0; i < newStudentsID.size(); i++) {
                if (stuID == newStudentsID.get(i)) {
                    // set the result into the attribute	 
                    request.setAttribute("reservations", reservations);
                    request.setAttribute("allReservations", allReservations);
                    request.setAttribute("newStudentsID", newStudentsID);
                    RequestDispatcher rd;
                    rd = getServletContext().getRequestDispatcher("/selectedStudentsReservations.jsp");
                    rd.forward(request, response);
                    return;
                }
            }
            reservations = db.queryRservationsByID(stuID);
            for (int i = 0; i < reservations.size(); i++) {
                ReservationBean b = reservations.get(i);
                allReservations.add(b);
            }

            studentsID.add(stuID);
            newStudentsID = removeDuplicates(studentsID);
            // set the result into the attribute	 
            request.setAttribute("reservations", reservations);
            request.setAttribute("allReservations", allReservations);
            request.setAttribute("newStudentsID", newStudentsID);
            // redirect the result to the borrowRequest.jsp
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/selectedStudentsReservations.jsp");
            rd.forward(request, response);
        } else if ("listAllEquipmentUtilizationRate".equalsIgnoreCase(action)) {
            // call the query db to get retrieve for all customer 
            ArrayList<ReservationBean> reservations = db.queryEquipmentUtilization();
            // call the query db to get retrieve for all customer 
            ArrayList<EquipmentBean> equipments = db.queryAllEquipment();
//            for (int i = 0; i < reservations.size(); i++) {
//                double totalRate = db.queryAllPeriod();
//                double rate = (reservations.get(i).getPeriod() / totalRate) * 100.0;
//                reservations.get(i).setUtilizationRate(rate);
//            }
            for (int i = 0; i < equipments.size(); i++) {
                for (int j = 0; j < reservations.size(); j++) {
                    double totalRate = db.queryAllPeriod();
                    double rate = (reservations.get(j).getPeriod() / totalRate) * 100.0;
                    reservations.get(j).setUtilizationRate(rate);
                    if (reservations.get(j).getEquipmentID() == equipments.get(i).getEquipmentID()) {
                        equipments.get(i).setUtilizationRate(rate);
                    }
                }
            }
            // set the result into the attribute	 
            request.setAttribute("reservations", reservations);
            // set the result into the attribute	 
            request.setAttribute("equipments", equipments);
            // redirect the result to the borrowRequest.jsp
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/equipmentUtilizationRate.jsp");
            rd.forward(request, response);
        } else if ("searchEquipmentUtilizationRate".equalsIgnoreCase(action)) {
            allEquipments = new ArrayList<EquipmentBean>();
            LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
            LocalDate endDate = LocalDate.parse(request.getParameter("endDate"));
            int equipmentID = Integer.parseInt(request.getParameter("equipmentID"));

            // call the query db to get retrieve for all customer 
            ArrayList<ReservationBean> reservations = db.queryEquipmentUtilizationByDate(startDate, endDate);
            // call the query db to get retrieve for all customer 
            EquipmentBean equipment = db.queryEquipmentByID(equipmentID);
            for (int i = 0; i < reservations.size(); i++) {
                double totalRate = db.queryPeriodByDate(startDate, endDate);
                double rate = (reservations.get(i).getPeriod() / totalRate) * 100.0;
                reservations.get(i).setUtilizationRate(rate);
                if (reservations.get(i).getEquipmentID() == equipment.getEquipmentID()) {
                    equipment.setUtilizationRate(rate);
                }
            }

            allEquipments.add(equipment);

            // set the result into the attribute	 
            request.setAttribute("reservations", reservations);
            // set the result into the attribute	 
            request.setAttribute("equipments", allEquipments);
            request.setAttribute("endDate", endDate);
            request.setAttribute("startDate", startDate);
            RequestDispatcher rd;
            rd = getServletContext().getRequestDispatcher("/equipmentUtilizationRate.jsp");
            rd.forward(request, response);
        }
    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {

        // Create a new ArrayList 
        ArrayList<T> newList = new ArrayList<T>();

        // Traverse through the first list 
        for (T element : list) {

            // If this element is not present in newList 
            // then add it 
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list 
        return newList;
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
