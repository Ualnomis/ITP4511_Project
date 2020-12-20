/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.test;

import ict.db.AssignmentDB;
import java.time.LocalDate;

/**
 *
 * @author 2689
 */
public class InitEntireDB {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String username = "root";
        String password = "";
        AssignmentDB db = new AssignmentDB(url, username, password);
        
        db.createDB();
        db.setUrl("jdbc:mysql://localhost:3306/itp4511_db");
        db.createTable();
        
        // init user
        db.addUserRecord(1, "Peter", "123", "Student");
        db.addUserRecord(2, "Faker", "123", "Technician");
        db.addUserRecord(3, "Hacker", "123", "Senior Technician");
        db.addUserRecord(4, "test", "123", "Student");
        db.addUserRecord(5, "Ptesteter", "123", "Student");
        db.addUserRecord(6, "Pettester", "123", "Student");
        
        
        // init equipment
        db.addEquipmentRecord("Wired Mouse", "available", "good", 20, true);
        db.addEquipmentRecord("Wireless Mouse", "available", "good", 30, true);
        db.addEquipmentRecord("Keyboard", "available", "good", 40, true);
        db.addEquipmentRecord("Notebook", "available", "good", 2, true);
        db.addEquipmentRecord("Mac Book Pro", "unavailable", "good", 0, false);
        db.addEquipmentRecord("ipad", "available", "good", 14, false);
        db.addEquipmentRecord("CD", "available", "good", 2, false);
        db.addEquipmentRecord("DVD", "available", "good", 5, false);
        db.addEquipmentRecord("USB", "available", "good", 7, false);
        db.addEquipmentRecord("Ryzen 3600x", "unavailable", "good", 0, true);
        
        // init reservation
        db.addReservationRecord(1, null, LocalDate.of(2020, 12, 31), null, 2, "Approved");
        db.addReservationRecord(1, null, LocalDate.of(2020, 12, 31), null, 2, "Reject");
        db.addReservationRecord(1, LocalDate.of(2020, 12, 1), LocalDate.of(2020, 12, 4), null, 2, "OnLease");
        db.addReservationRecord(5, LocalDate.of(2020, 12, 4), LocalDate.of(2020, 12, 31), LocalDate.of(2020, 12, 21), 2, "Completed");       
        
        // init reservation equipment
        db.addReservationEquipment(1, 1, 5);
        db.addReservationEquipment(2, 2, 10);
        db.addReservationEquipment(3, 3, 30);
    }
}
