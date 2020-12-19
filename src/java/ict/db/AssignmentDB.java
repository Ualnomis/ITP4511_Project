/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

/**
 *
 * @author 2689
 */

import ict.bean.EquipmentBean;
import ict.bean.UserBean;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AssignmentDB {
    // variable dictionary
    private String url; // url of database
    private String username; // database login username
    private String password; // database login password
    
    // constructor
    public AssignmentDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    // get conneciton
    public Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }
    
    // create database
    public void createDB(){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try{
            cnnct = getConnection();
            String  preQueryStatement = "Create database itp4511_db";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.executeUpdate();
            System.out.println("database is created");
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    // create all table
    public void createTable() {
        Statement stmnt = null;
        Connection cnnct = null;
        try{
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            // user table
            String sql =
                    "CREATE TABLE IF NOT EXISTS User(" +
                    "user_id varchar(9) NOT NULL," +
                    "name varchar(25) NOT NULL," +
                    "password varchar(25) NOT NULL," +
                    "role varchar(25) NOT NULL," +
                    "PRIMARY KEY (userId))";
            stmnt.execute(sql);
            System.out.println("User table created");
            
            // Equipment table
            sql = "Create table if not exists Equipment ("
                    + "equipment_id int(8) NOT NULL AUTO_INCREMENT,"
                    + "equipment_name varchar(40) NOT NULL,"
                    + "status varchar(20) NOT NULL,"
                    + "description varchar(100) DEFAULT NULL,"
                    + "stock int(8) NOT NULL,"
                    + "visibility boolean NOT NULL,"
                    + "PRIMARY KEY (equipment_id))";
            stmnt.execute(sql);
            
           
            // reservation record(reservation id, submitUser, approved user, status)
            // status(pending, approved, rejected, onLease, completed, overdue)
            sql = "CREATE TABLE IF NOT EXISTS Reservation("
                    + "reservation_id int(9) NOT NULL AUTO_INCREMENT,"
                    + "submit_user_id int(9) NOT NULL,"
                    + "checkout_date date,"
                    + "due_date date"
                    + "return_date date"
                    + "approve_user_id int(9)"
                    + "status varchar(20) NOT NULL,"
                    + "PRIMARY KEY (reservation_id),"
                    + "FOREGIN KEY submit_user_id REFERENCES User(user_id),"
                    + "FOREGIN KEY approve_user_id REFERENCES User(user_id)";
            System.out.println("Reservation is created");
            
            // reservation equipment(fk1: reservation id, fk2: equipment_id)
            sql = "CREATE TABLE IF NOT EXISTS ReservationEquipment(" 
                    + "reservation_id int(9),"
                    + "equipment_id int(9),"
                    + "FOREIGN KEY (reservation_id) REFERENCES Reservation(reservation_id),"
                    + "FOREIGN KEY (equipment_id) REFERENCES Equipment(equipment_id)";
            
            // check out record(fk reservation id, date)
            
//            sql = "CREATE TABLE IF NOT EXISTS BorrowList("
//                    + "borrow_id int(9) NOT NULL AUTO_INCREMENT,"
//                    + "equipment_id int(8) NOT NULL,"
//                    + "userId varchar(10) NOT NULL,"
//                    + "quantity int(3) NOT NULL,"
//                    + "status varchar(20) NOT NULL,"
//                    + "PRIMARY KEY (borrow_id))";
//            stmnt.execute(sql);
//            System.out.println("BorrowList table created");
//            
//            sql
//                    = "CREATE TABLE CheckInOut ("
//                    + "  borrow_id int(10) NOT NULL,"
//                    + "  start date NOT NULL ,"
//                    + "  end date,"
//                    + "PRIMARY KEY (borrow_id))";
//            stmnt.execute(sql);
//            System.out.println("CheckInOut table created");
            
            stmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex!= null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    } 
    
    // add user record
    public boolean addUserRecord(String userid, String name, String pw, String role){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try{
            cnnct = getConnection();
            String preQueryStatement = "Insert into users values (?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1,userid);
            pStmnt.setString(2,name);
            pStmnt.setString(3,pw);
            pStmnt.setString(4,role);
            int rowCount = pStmnt.executeUpdate();
            if(rowCount >=1){
                isSuccess = true;
                System.out.println("UserDB: " + name + " is added");
            }
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return isSuccess;
    }
    
    // add equipment record
    // INSERT INTO `equipment` (`equipment_id`, `equipment_name`, `status`, `description`, `stock`, `visibility`) VALUES (NULL, 'a', 'a', 'a', '5', 'a');
    public boolean addEquipmentRecord(String name, String status, String description, int stock, boolean visibility) {
                Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Insert into equipment(equipment_name,status,description,stock,visibility) values (?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
//            pStmnt.setInt(1,equipment_id);
            pStmnt.setString(1, name);
            pStmnt.setString(2, status);
            pStmnt.setString(3, description);
            pStmnt.setInt(4, stock);
            pStmnt.setBoolean(5, visibility);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(name + " is added");
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSuccess;
    }
    
    // add Reservation record
    public boolean addReservationRecord(int submitUserID, LocalDate checkoutDate, LocalDate dueDate, LocalDate returnDate, int approveUserID, String status) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO Reservation(submit_user_id, chekcout_date, due_date, return_date, approve_user_id, status) values (?,?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, submitUserID);
            pStmnt.setObject(2, checkoutDate);
            pStmnt.setObject(3, dueDate);
            pStmnt.setObject(4, returnDate);
            pStmnt.setInt(5, approveUserID);
            pStmnt.setString(6, status);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println("reservation is added");
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSuccess;
    }
    
    // add Reservation equipment record
    public boolean addReservationEquipment(int reservationID, int equipmentID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "insert into reservationequipment(reservation_id, equipment_id) values(?, ?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, reservationID);
            pStmnt.setInt(2, equipmentID);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println("reservation is added");
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSuccess;
    }
    
    public boolean addReservationEquipment(int reservationID, EquipmentBean equipmentBean) {
       Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "insert into reservationequipment(reservation_id, equipment_id) values(?, ?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, reservationID);
            pStmnt.setInt(2, equipmentBean.getEquipmentID());
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println("reservation is added");
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSuccess;
    }
    
    public boolean addReservationEquipment(int reservationID, ArrayList<EquipmentBean> equipments) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();

            for (EquipmentBean equipment : equipments) {
                String preQueryStatement = "insert into reservationequipment(reservation_id, equipment_id) values(?, ?)";
                pStmnt = cnnct.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, reservationID);
                pStmnt.setInt(2, equipment.getEquipmentID());

            }
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println("reservation is added");
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return isSuccess;
    }
    
    // check is valid user
    public String isValidUser(String userId, String pw) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        String userRole = "";
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM users WHERE userId = ? and password = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, userId);
            pStmnt.setString(2, pw);
            ResultSet rs = pStmnt.executeQuery();
            if (rs.next()) {
                userRole = rs.getString(4);
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return userRole;
    }
    
    // list all user
    public ArrayList<UserBean> queryAllUser() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean userBean = null;
        ArrayList<UserBean> arraylist= new ArrayList<UserBean>();
        try{
            cnnct = getConnection();
            String  preQueryStatement = "Select * from users";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                userBean = new UserBean();
                userBean.setUserId(rs.getString(1));
                userBean.setName(rs.getString(2));
                userBean.setPw(rs.getString(3));
                userBean.setRole(rs.getString(4));
                arraylist.add(userBean);
            }
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return arraylist;
    }
    
    // list user record by id
    public UserBean queryUserById(String id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean userBean = null;
        try{
            cnnct = getConnection();
            String  preQueryStatement = "Select * from users where userId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1,id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if(rs.next()){
                userBean = new UserBean();
                userBean.setUserId(rs.getString(1));
                userBean.setName(rs.getString(2));
                userBean.setPw(rs.getString(3));
                userBean.setRole(rs.getString(4));
            }
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return userBean;
    }
    
    // list user record by name
    public ArrayList<UserBean> queryUserByName(String name){
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean userBean = null;
        ArrayList<UserBean> arraylist= new ArrayList<UserBean>();
        try{
            cnnct = getConnection();
            String  preQueryStatement = "Select * from users where name like ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1,"%"+name+"%");
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                userBean = new UserBean();
                userBean.setUserId(rs.getString(1));
                userBean.setName(rs.getString(2));
                userBean.setPw(rs.getString(3));
                userBean.setRole(rs.getString(4));
                arraylist.add(userBean);
            }
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return arraylist;
    }
    
    // list user record by role
    public ArrayList<UserBean> queryUserByRole(String role) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean userBean = null;
        ArrayList<UserBean> arraylist= new ArrayList<UserBean>();
        try{
            cnnct = getConnection();
            String  preQueryStatement = "Select * from users where role = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, role);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while(rs.next()){
                userBean = new UserBean();
                userBean.setUserId(rs.getString(1));
                userBean.setName(rs.getString(2));
                userBean.setPw(rs.getString(3));
                userBean.setRole(rs.getString(4));
                arraylist.add(userBean);
            }
            pStmnt.close();
            cnnct.close();
        }catch(SQLException ex){
            while(ex !=null){
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return arraylist;
    }
    
    
}