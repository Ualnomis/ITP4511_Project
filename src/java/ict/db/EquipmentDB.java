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

public class EquipmentDB {
    private String url;
    private String username;
    private String password;

    public EquipmentDB(String url, String username, String password) {
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
                    = "Create table if not exists equipment ("
                    + "equipment_id int(8) NOT NULL AUTO_INCREMENT,"
                    + "equipment_name varchar(40) NOT NULL,"
                    + "status varchar(20) NOT NULL,"
                    + "description varchar(100) DEFAULT NULL,"
                    + "stock int(8) NOT NULL,"
                    + "visibility varchar(10) NOT NULL,"
                    + "primary key (equipment_id))";
            stmnt.execute(sql);
            stmnt.close();
            cnnct.close();
            System.out.println("equipment is added");
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
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
        
    
}
