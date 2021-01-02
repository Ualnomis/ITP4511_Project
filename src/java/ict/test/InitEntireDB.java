package ict.test;

import ict.db.AssignmentDB;
import java.time.LocalDate;

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
        db.addUserRecord("stu1@vtc.hk", "Peter", "123", "M", "12345678", "Student", true);
        db.addUserRecord("tec@vtc.hk", "Technician1", "123", "F", "12345678", "Technician", true);
        db.addUserRecord("stec@vtc.hk", "Stec1", "123", "M", "12345678", "Senior Technician", true);
        db.addUserRecord("stu2@vtc.hk", "Alan", "123", "F", "12345678", "Student", true);
        db.addUserRecord("stu3@vtc.hk", "Simon", "123", "M", "12345678", "Student", true);
        
        // init equipment
        db.addEquipmentRecord("Wired Mouse", "available", "good mouse", 20, true);
        db.addEquipmentRecord("Wireless Mouse", "available", "test", 30, true);
        db.addEquipmentRecord("Keyboard", "available", "good", 40, true);
        db.addEquipmentRecord("Notebook", "available", "good", 2, true);
        db.addEquipmentRecord("Mac Book Pro", "unavailable", "good", 0, false);
        db.addEquipmentRecord("ipad", "available", "good", 14, false);
        db.addEquipmentRecord("CD", "available", "good", 2, false);
        db.addEquipmentRecord("DVD", "available", "good", 5, false);
        db.addEquipmentRecord("USB", "available", "good", 7, true);
        db.addEquipmentRecord("pen", "unavailable", "good", 0, true);
        
        // init reservation
        db.addReservationRecord(1, 1, 5, LocalDate.of(2020, 12, 18), LocalDate.of(2020, 12, 20), LocalDate.of(2020, 12, 31), null, null, 11, 0, "Requesting");
        db.addReservationRecord(4, 2, 5, LocalDate.of(2020, 12, 19), LocalDate.of(2020, 12, 20), LocalDate.of(2020, 12, 31), null, null, 11, 2, "Approved");
        db.addReservationRecord(5, 2, 5, LocalDate.of(2020, 12, 20), LocalDate.of(2020, 12, 20), LocalDate.of(2020, 12, 31), null, null, 11, 2, "Rejected");
        db.addReservationRecord(1, 3, 5, LocalDate.of(2020, 12, 20), LocalDate.of(2020, 12, 20), LocalDate.of(2020, 12, 31), LocalDate.of(2020, 12, 20), null, 11, 2, "Leasing");
        db.addReservationRecord(4, 1, 5, LocalDate.of(2020, 12, 15), LocalDate.of(2020, 12, 16), LocalDate.of(2020, 12, 18), LocalDate.of(2020, 12, 16), LocalDate.of(2020, 12, 18), 2, 2, "Completed");       
    }
}
