/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.test;

import ict.bean.ReservationBean;
import ict.db.AssignmentDB;
import java.util.ArrayList;

/**
 *
 * @author 2689
 */
public class testQueryReservation {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "";
        AssignmentDB db = new AssignmentDB(url, username, password);
        db.setUrl("jdbc:mysql://localhost:3306/itp4511_db");
        
        ArrayList<ReservationBean> test = db.queryAllReservation();
        for (int i = 0; i < test.size(); i++) {
            System.out.println(test.get(i).getReservationID());
            System.out.println(test.get(i).getSubmitUserID());
            System.out.println(test.get(i).getEquipmentID());
            System.out.println(test.get(i).getQty());
            System.out.println(test.get(i).getRequestDate());
            System.out.println(test.get(i).getStartDate());
            System.out.println(test.get(i).getDueDate());
            System.out.println(test.get(i).getCheckOutDate());
            System.out.println(test.get(i).getCheckInDate());
            System.out.println(test.get(i).getEquipmentName());
            System.out.println(test.get(i).getStatus());
            System.out.println("-----------------------------------------------");
        }
    }
}
