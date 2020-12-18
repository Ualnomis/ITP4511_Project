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

import java.io.*;
import java.sql.*;
import java.time.LocalDate;

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
            String sql =
                    "CREATE TABLE IF NOT EXISTS User(" +
                    "userId varchar(10) NOT NULL," +
                    "name varchar(25) NOT NULL," +
                    "password varchar(25) NOT NULL," +
                    "role varchar(25) NOT NULL," +
                    "PRIMARY KEY (userId))";
            stmnt.execute(sql);
            System.out.println("User table created");
            
            sql
                    = "CREATE TABLE IF NOT EXISTS equipment("
                    + "equipment_id int(8) NOT NULL AUTO_INCREMENT,"
                    + "equipment_name varchar(40) NOT NULL,"
                    + "status varchar(20) NOT NULL,"
                    + "description varchar(100) DEFAULT NULL,"
                    + "stock int(8) NOT NULL,"
                    + "visibility varchar(10) NOT NULL,"
                    + "PRIMARY KEY (equipment_id))";
            stmnt.execute(sql);
            System.out.println("Equipment table created");
            
            sql = "CREATE TABLE IF NOT EXISTS BorrowList("
                    + "borrow_id int(10) NOT NULL AUTO_INCREMENT,"
                    + "equipment_id int(8) NOT NULL,"
                    + "userId varchar(10) NOT NULL,"
                    + "quantity int(3) NOT NULL,"
                    + "status varchar(20) NOT NULL,"
                    + "PRIMARY KEY (borrow_id))";
            stmnt.execute(sql);
            System.out.println("BorrowList table created");
            
            sql
                    = "CREATE TABLE CheckInOut ("
                    + "  borrow_id int(10) NOT NULL,"
                    + "  start date NOT NULL ,"
                    + "  end date,"
                    + "PRIMARY KEY (borrow_id))";
            stmnt.execute(sql);
            System.out.println("CheckInOut table created");
            
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
    
    // add borrowlist record
    public boolean addRecord(int equipment_id, String userId, int quantity, String status) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Insert into borrowlist(equipment_id,userId,quantity,status) values (?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, equipment_id);
            pStmnt.setString(2, userId);
            pStmnt.setInt(3, quantity);
            pStmnt.setString(4, status);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(equipment_id + ": " + quantity + " is added");
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
    
    // add checkinout record
    public boolean addRecord(int borrow_id, LocalDate start, LocalDate end) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Insert into check_in_out values (?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, borrow_id);
            pStmnt.setObject(2, start);
            pStmnt.setObject(3, end);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(borrow_id + " is added");
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
    
    // add equipment record
    public boolean addRecord(String equipment_name, String status, String description, int stock, String visibility) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "Insert into equipment(equipment_name,status,description,stock,visibility) values (?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
//            pStmnt.setInt(1,equipment_id);
            pStmnt.setString(1, equipment_name);
            pStmnt.setString(2, status);
            pStmnt.setString(3, description);
            pStmnt.setInt(4, stock);
            pStmnt.setString(5, visibility);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(equipment_name + " is added");
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
    
    // 
}
