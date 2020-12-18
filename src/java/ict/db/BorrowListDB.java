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

public class BorrowListDB {

    private String url;
    private String username;
    private String password;

    public BorrowListDB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }

    public void createTable() {
        Statement stmnt = null;
        Connection cnnct = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql
                    = "Create table if not exists borrowlist ("
                    + "borrow_id int(10) NOT NULL AUTO_INCREMENT,"
                    + "equipment_id int(8) NOT NULL,"
                    + "userId varchar(10) NOT NULL,"
                    + "quantity int(3) NOT NULL,"
                    + "status varchar(20) NOT NULL,"
                    + "primary key (borrow_id))";
            stmnt.execute(sql);
            stmnt.close();
            cnnct.close();
            System.out.println("borrowlist is added");
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
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
}
