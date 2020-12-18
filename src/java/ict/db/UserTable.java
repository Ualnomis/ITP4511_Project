/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ict.db;

import java.sql.*;
import java.io.IOException;

/**
 *
 * @author 2689
 */
public class UserTable {
    private String url;
    private String username;
    private String password;

    public UserTable(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
    public Connection getConnection() throws SQLException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }
    
    public void createUserTable() {
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
    
        public boolean addRecord(String userid, String name, String pw, String role){
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
        
    
}
