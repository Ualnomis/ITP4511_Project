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
import ict.bean.ReservationBean;
import ict.bean.UserBean;
import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class AssignmentDB {

    // variable dictionary
    private String url; // url of database
    private String username; // database login username
    private String password; // database login password

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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
    public void createDB() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "Create database itp4511_db";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.executeUpdate();
            System.out.println("database is created");
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
    }

    // create all table
    public void createTable() {
        Statement stmnt = null;
        Connection cnnct = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            // user table
            String sql
                    = "CREATE TABLE IF NOT EXISTS User("
                    + "user_id int(9) NOT NULL,"
                    + "name varchar(25) NOT NULL,"
                    + "password varchar(25) NOT NULL,"
                    + "role varchar(25) NOT NULL,"
                    + "PRIMARY KEY (user_id))";
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
            System.out.println("Equipment is created");

            // reservation record(reservation id, submitUser, approved user, status)
            // status(pending, approved, rejected, onLease, completed, overdue)
            sql = "CREATE TABLE IF NOT EXISTS Reservation("
                    + "reservation_id int(9) NOT NULL AUTO_INCREMENT,"
                    + "submit_user_id int(9) NULL,"
                    + "checkout_date date,"
                    + "due_date date,"
                    + "return_date date,"
                    + "approve_user_id int(9) NULL,"
                    + "status varchar(20),"
                    + "PRIMARY KEY (reservation_id),"
                    + "CONSTRAINT submit_user_id_fk FOREIGN KEY (submit_user_id) REFERENCES User(user_id),"
                    + "CONSTRAINT approve_user_id_fk FOREIGN KEY (approve_user_id) REFERENCES User(user_id))";
            stmnt.execute(sql);
            System.out.println("Reservation is created");

            // reservation equipment(fk1: reservation id, fk2: equipment_id)
            sql = "CREATE TABLE IF NOT EXISTS ReservationEquipment("
                    + "reservation_id int(9),"
                    + "equipment_id int(9),"
                    + "qty int(9),"
                    + "FOREIGN KEY (reservation_id) REFERENCES Reservation(reservation_id),"
                    + "FOREIGN KEY (equipment_id) REFERENCES Equipment(equipment_id))";
            stmnt.execute(sql);
            System.out.println("ReservationEquipment is created");
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
        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // add user record
    public boolean addUserRecord(int userid, String name, String pw, String role) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO User values (?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, userid);
            pStmnt.setString(2, name);
            pStmnt.setString(3, pw);
            pStmnt.setString(4, role);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println("UserDB: " + name + " is added");
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
            String preQueryStatement = "INSERT INTO Reservation(submit_user_id, checkout_date, due_date, return_date, approve_user_id, status) values (?,?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, submitUserID);
            if (checkoutDate != null) {
                pStmnt.setObject(2, Date.valueOf(checkoutDate));
            } else {
                pStmnt.setObject(2, null);
            }
            if (dueDate != null) {
                pStmnt.setObject(3, Date.valueOf(dueDate));
            } else {
                pStmnt.setObject(3, null);
            }
            if (returnDate != null) {
                pStmnt.setObject(4, Date.valueOf(returnDate));
            } else {
                pStmnt.setObject(4, null);
            }
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
    public boolean addReservationEquipment(int reservationID, int equipmentID, int qty) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "insert into reservationequipment(reservation_id, equipment_id, qty) values(?, ?, ?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, reservationID);
            pStmnt.setInt(2, equipmentID);
            pStmnt.setInt(3, qty);
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

    public boolean addReservationEquipment(int reservationID, EquipmentBean equipmentBean, int qty) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "insert into reservationequipment(reservation_id, equipment_id, qty) values(?, ?, ?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, reservationID);
            pStmnt.setInt(2, equipmentBean.getEquipmentID());
            pStmnt.setInt(3, qty);
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

    public boolean addReservationEquipment(int reservationID, ArrayList<EquipmentBean> equipments, int qty) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();

            for (EquipmentBean equipment : equipments) {
                String preQueryStatement = "insert into reservationequipment(reservation_id, equipment_id, qty) values(?, ?, ?)";
                pStmnt = cnnct.prepareStatement(preQueryStatement);
                pStmnt.setInt(1, reservationID);
                pStmnt.setInt(2, equipment.getEquipmentID());
                pStmnt.setInt(3, qty);
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
    public boolean isValidUser(int userID, String pw) throws SQLException, IOException {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isValid = false;
        cnnct = getConnection();
        String preQueryStatement = "SELECT * FROM USER WHERE user_id=? and password=?";
        pStmnt = cnnct.prepareStatement(preQueryStatement);
        pStmnt.setInt(1, userID);
        pStmnt.setString(2, pw);
        ResultSet rs = null;
        rs = pStmnt.executeQuery();
        if (rs.next()) {
            isValid = true;
        } else {
            isValid = false;
        }
        pStmnt.close();
        cnnct.close();
        return isValid;
    }

    // list all user
    public ArrayList<UserBean> queryAllUser() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean userBean = null;
        ArrayList<UserBean> arraylist = new ArrayList<UserBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM User";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                userBean = new UserBean();
                userBean.setUserID(rs.getInt(1));
                userBean.setName(rs.getString(2));
                userBean.setPw(rs.getString(3));
                userBean.setRole(rs.getString(4));
                arraylist.add(userBean);
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
        return arraylist;
    }

    // list user record by id
    public UserBean queryUserByID(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean userBean = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM User WHERE user_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                userBean = new UserBean();
                userBean.setUserID(rs.getInt(1));
                userBean.setName(rs.getString(2));
                userBean.setPw(rs.getString(3));
                userBean.setRole(rs.getString(4));
            }
            pStmnt.close();
            cnnct.close();
        } catch (SQLException ex) {
            while (ex != null) {
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return userBean;
    }

    // list user record by name
    public ArrayList<UserBean> queryUserByName(String name) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean userBean = null;
        ArrayList<UserBean> arraylist = new ArrayList<UserBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from users where name like ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, "%" + name + "%");
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                userBean = new UserBean();
                userBean.setUserID(rs.getInt(1));
                userBean.setName(rs.getString(2));
                userBean.setPw(rs.getString(3));
                userBean.setRole(rs.getString(4));
                arraylist.add(userBean);
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
        return arraylist;
    }

    // list user record by role
    public ArrayList<UserBean> queryUserByRole(String role) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean userBean = null;
        ArrayList<UserBean> arraylist = new ArrayList<UserBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "Select * from USER where role = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, role);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                userBean = new UserBean();
                userBean.setUserID(rs.getInt(1));
                userBean.setName(rs.getString(2));
                userBean.setPw(rs.getString(3));
                userBean.setRole(rs.getString(4));
                arraylist.add(userBean);
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
        return arraylist;
    }

    // edit user record
    public boolean editUserRecord(UserBean ub) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE User SET name=?, password=?, role=? where user_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, ub.getName());
            pStmnt.setString(2, ub.getPw());
            pStmnt.setString(3, ub.getRole());
            pStmnt.setInt(4, ub.getUserID());
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(ub.getUserID() + " is updated");
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

    // edit Equipment
    public boolean editEquipmentRecord(EquipmentBean eb) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE Equipment SET equipment_name = ?, status = ?, description = ?, visibility = ?, stock = ? WHERE equipment_id = ?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, eb.getEquipmentName());
            pStmnt.setString(2, eb.getStatus());
            pStmnt.setString(3, eb.getDescription());
            pStmnt.setBoolean(4, eb.isVisibility());
            pStmnt.setInt(5, eb.getStock());
            pStmnt.setInt(6, eb.getEquipmentID());
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(eb.getEquipmentID() + " is updated");
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

    // edit Reservation
    public boolean editReservationRecord(ReservationBean rb) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE Reservation SET submit_user_id = ?, checkout_date = ?, due_date = ?, return_date = ?, approve_user_id = ?, status = ? where userId=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, rb.getSubmitUserID());
            pStmnt.setObject(2, rb.getCheckoutDate());
            pStmnt.setObject(3, rb.getDueDate());
            pStmnt.setObject(4, rb.getReturnDate());
            pStmnt.setInt(5, rb.getApproveUserID());
            pStmnt.setString(6, rb.getStatus());
            pStmnt.setInt(7, rb.getReservationID());

            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(rb.getReservationID() + " is updated");
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

    // drop the database
    public void dropDB() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        try {
            cnnct = getConnection();
            String preQueryStatement = "drop database itp4511_db";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.executeUpdate();
            System.out.println("database is dropped");
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
    }

    // remove user record
    public boolean delUserRecord(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int num = 0;
        try {
            cnnct = getConnection();
            String sql = "SET foreign_key_checks = 0";
            pStmnt = cnnct.prepareStatement(sql);
            pStmnt.executeUpdate();
            String preQueryStatement = "DELETE FROM User WHERE user_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, id);
            num = pStmnt.executeUpdate();
            sql = "SET foreign_key_checks = 1";
            pStmnt = cnnct.prepareStatement(sql);
            pStmnt.executeUpdate();

        } catch (SQLException ex) {
            while (ex != null) {
                ex.printStackTrace();
                ex = ex.getNextException();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (pStmnt != null) {
                try {
                    pStmnt.close();
                } catch (SQLException e) {
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException sqlEx) {
                }
            }
        }
        return (num == 1) ? true : false;
    }
}
