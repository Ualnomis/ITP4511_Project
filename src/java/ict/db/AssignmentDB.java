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
                    + "user_id int(9) NOT NULL AUTO_INCREMENT,"
                    + "email varchar(50) NOT NULL,"
                    + "name varchar(25) NOT NULL,"
                    + "password varchar(25) NOT NULL,"
                    + "gender varchar(8) NOT NULL,"
                    + "phone varchar(8) NOT NULL,"
                    + "role varchar(25) NOT NULL,"
                    + "status boolean NOT NULL,"
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
                    + "equipment_id int(9),"
                    + "qty int(9),"
                    + "request_date date,"
                    + "start_date date,"
                    + "due_date date,"
                    + "checkout_date date,"
                    + "checkin_date date,"
                    + "period int(9),"
                    + "status varchar(20),"
                    + "approve_user_id int(9) NULL,"
                    + "PRIMARY KEY (reservation_id),"
                    + "CONSTRAINT submit_user_id_fk FOREIGN KEY (submit_user_id) REFERENCES User(user_id),"
                    + "CONSTRAINT equipment_id_fk FOREIGN KEY (equipment_id) REFERENCES Equipment(equipment_id),"
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
    public boolean addUserRecord(String email, String name, String password, String gender, String phone, String role, boolean status) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            if (isExistUser(email)) {
                return false;
            }
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO User(email, name, password, gender, phone, role, status) values (?,?,?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, email);
            pStmnt.setString(2, name);
            pStmnt.setString(3, password);
            pStmnt.setString(4, gender);
            pStmnt.setString(5, phone);
            pStmnt.setString(6, role);
            pStmnt.setBoolean(7, status);
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
    public boolean addReservationRecord(int submitUserID, int equipmentID, int qty, LocalDate requestDate, LocalDate startDate, LocalDate dueDate, LocalDate checkOutDate, LocalDate checkInDate, int period, int approveUserID, String status) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;
        try {
            cnnct = getConnection();
            String preQueryStatement = "INSERT INTO Reservation(submit_user_id, equipment_id, qty, request_date, start_date, due_date, checkout_date, checkin_date, period, status, approve_user_id) values (?,?,?,?,?,?,?,?,?,?,?)";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, submitUserID);
            pStmnt.setInt(2, equipmentID);
            pStmnt.setInt(3, qty);
            if (requestDate != null) {
                pStmnt.setObject(4, Date.valueOf(requestDate));
            } else {
                pStmnt.setObject(4, null);
            }
            if (startDate != null) {
                pStmnt.setObject(5, Date.valueOf(startDate));
            } else {
                pStmnt.setObject(5, null);
            }
            if (dueDate != null) {
                pStmnt.setObject(6, Date.valueOf(dueDate));
            } else {
                pStmnt.setObject(6, null);
            }
            if (checkOutDate != null) {
                pStmnt.setObject(7, Date.valueOf(checkOutDate));
            } else {
                pStmnt.setObject(7, null);
            }
            if (checkInDate != null) {
                pStmnt.setObject(8, Date.valueOf(checkInDate));
            } else {
                pStmnt.setObject(8, null);
            }
            pStmnt.setInt(9, period);
            pStmnt.setString(10, status);
            if (approveUserID > 0) {
                pStmnt.setInt(11, approveUserID);
            } else {
                pStmnt.setString(11, null);
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
    public boolean isValidUser(String email, String pw) throws SQLException, IOException {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isValid = false;
        cnnct = getConnection();
        String preQueryStatement = "SELECT * FROM USER WHERE email=? and password=? and status=true";
        pStmnt = cnnct.prepareStatement(preQueryStatement);
        pStmnt.setString(1, email);
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

    // check is user email exist
    public boolean isExistUser(String email) throws SQLException, IOException {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isExist = false;
        cnnct = getConnection();
        String preQueryStatement = "SELECT * FROM USER WHERE email=?";
        pStmnt = cnnct.prepareStatement(preQueryStatement);
        pStmnt.setString(1, email);
        ResultSet rs = null;
        rs = pStmnt.executeQuery();
        if (rs.next()) {
            isExist = true;
        } else {
            isExist = false;
        }
        pStmnt.close();
        cnnct.close();
        return isExist;
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
                userBean.setEmail(rs.getString(2));
                userBean.setName(rs.getString(3));
                userBean.setPassword(rs.getString(4));
                userBean.setGender(rs.getString(5));
                userBean.setPhone(rs.getString(6));
                userBean.setRole(rs.getString(7));
                userBean.setStatus(rs.getBoolean(8));
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
                userBean.setEmail(rs.getString(2));
                userBean.setName(rs.getString(3));
                userBean.setPassword(rs.getString(4));
                userBean.setGender(rs.getString(5));
                userBean.setPhone(rs.getString(6));
                userBean.setRole(rs.getString(7));
                userBean.setStatus(rs.getBoolean(8));
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

    // list user record by email
    public UserBean queryUserByEmail(String email) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean userBean = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM User WHERE email=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, email);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                userBean = new UserBean();
                userBean.setUserID(rs.getInt(1));
                userBean.setEmail(rs.getString(2));
                userBean.setName(rs.getString(3));
                userBean.setPassword(rs.getString(4));
                userBean.setGender(rs.getString(5));
                userBean.setPhone(rs.getString(6));
                userBean.setRole(rs.getString(7));
                userBean.setStatus(rs.getBoolean(8));
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

    // edit user record
    public boolean editUserRecord(UserBean ub) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE User SET name=?, password=?, gender=?, phone=?, role=?, status=? where user_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, ub.getName());
            pStmnt.setString(2, ub.getPassword());
            pStmnt.setString(3, ub.getGender());
            pStmnt.setString(4, ub.getPhone());
            pStmnt.setString(5, ub.getRole());
            pStmnt.setBoolean(6, ub.isStatus());
            pStmnt.setInt(7, ub.getUserID());
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
            String preQueryStatement = "UPDATE Equipment SET equipment_name=?, status=?, description=?, visibility=?, stock=? WHERE equipment_id=?";
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

    // disable user record
    public boolean disableUser(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int num = 0;
        try {
            cnnct = getConnection();
            String sql = "SET foreign_key_checks = 0";
            pStmnt = cnnct.prepareStatement(sql);
            pStmnt.executeUpdate();
            String preQueryStatement = "UPDATE User SET status=false WHERE user_id=?";
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

    // list all user
    public ArrayList<EquipmentBean> queryAllEquipment() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean userBean = null;
        ArrayList<EquipmentBean> arraylist = new ArrayList<EquipmentBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Equipment";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                EquipmentBean eb = new EquipmentBean();
                eb.setEquipmentID(rs.getInt(1));
                eb.setEquipmentName(rs.getString(2));
                eb.setStatus(rs.getString(3));
                eb.setDescription(rs.getString(4));
                eb.setStock(rs.getInt(5));
                eb.setVisibility(rs.getBoolean(6));
                arraylist.add(eb);
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

    // del equipment record
    public boolean delEquipmentRecord(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int num = 0;
        try {
            cnnct = getConnection();
            String sql = "SET foreign_key_checks = 0";
            pStmnt = cnnct.prepareStatement(sql);
            pStmnt.executeUpdate();
            String preQueryStatement = "DELETE FROM Equipment WHERE equipment_id=?";
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

    // queryEquipmentByID
    public EquipmentBean queryEquipmentByID(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        EquipmentBean eb = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Equipment WHERE equipment_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            pStmnt.setInt(1, id);
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                eb = new EquipmentBean();
                eb.setEquipmentID(rs.getInt(1));
                eb.setEquipmentName(rs.getString(2));
                eb.setStatus(rs.getString(3));
                eb.setDescription(rs.getString(4));
                eb.setStock(rs.getInt(5));
                eb.setVisibility(rs.getBoolean(6));
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
        return eb;
    }

    // query all equipment borrowable
    public ArrayList<EquipmentBean> queryAllEquipmentBorrowable() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        UserBean userBean = null;
        ArrayList<EquipmentBean> arraylist = new ArrayList<EquipmentBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Equipment WHERE status=\"available\" and visibility=true and stock > 0";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                EquipmentBean eb = new EquipmentBean();
                eb.setEquipmentID(rs.getInt(1));
                eb.setEquipmentName(rs.getString(2));
                eb.setStatus(rs.getString(3));
                eb.setDescription(rs.getString(4));
                eb.setStock(rs.getInt(5));
                eb.setVisibility(rs.getBoolean(6));
                arraylist.add(eb);
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

    public ArrayList<ReservationBean> queryAllReservation() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<ReservationBean> arraylist = new ArrayList<ReservationBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Reservation";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                ReservationBean b = new ReservationBean();
                b.setReservationID(rs.getInt(1));
                b.setSubmitUserID(rs.getInt(2));
                b.setEquipmentID(rs.getInt(3));
                b.setQty(rs.getInt(4));
                b.setRequestDate(LocalDate.parse(rs.getString(5)));
                b.setStartDate(LocalDate.parse(rs.getString(6)));
                b.setDueDate(LocalDate.parse(rs.getString(7)));
                if (rs.getString(8) != null) {
                    b.setCheckOutDate(LocalDate.parse(rs.getString(8)));
                } else {
                    b.setCheckOutDate(null);
                }
                if (rs.getString(9) != null) {
                    b.setCheckInDate(LocalDate.parse(rs.getString(9)));
                } else {
                    b.setCheckInDate(null);
                }
                b.setPeriod(rs.getInt(10));
                b.setStatus(rs.getString(11));
                if (rs.getString(12) != null) {
                    b.setApproveUserID(Integer.parseInt(rs.getString(12)));
                } else {
                    b.setApproveUserID(0);
                }
                b.setSubmitUserName(queryUserNameByID(rs.getInt(2)));
                b.setApproveUserName(queryUserNameByID(b.getApproveUserID()));
                b.setEquipmentName(queryEquipmentNameByID(rs.getInt(3)));
                arraylist.add(b);
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

    public String queryUserNameByID(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        String name = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT name FROM User WHERE user_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            pStmnt.setInt(1, id);
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                name = rs.getString(1);
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
        return name;
    }

    public String queryEquipmentNameByID(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        String name = null;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT equipment_name FROM Equipment WHERE equipment_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            pStmnt.setInt(1, id);
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                name = rs.getString(1);
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
        return name;
    }

    public ArrayList<ReservationBean> queryRequestReservation() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<ReservationBean> arraylist = new ArrayList<ReservationBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Reservation WHERE status=\"Requesting\"";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                ReservationBean b = new ReservationBean();
                b.setReservationID(rs.getInt(1));
                b.setSubmitUserID(rs.getInt(2));
                b.setEquipmentID(rs.getInt(3));
                b.setQty(rs.getInt(4));
                b.setRequestDate(LocalDate.parse(rs.getString(5)));
                b.setStartDate(LocalDate.parse(rs.getString(6)));
                b.setDueDate(LocalDate.parse(rs.getString(7)));
                if (rs.getString(8) != null) {
                    b.setCheckOutDate(LocalDate.parse(rs.getString(8)));
                } else {
                    b.setCheckOutDate(null);
                }
                if (rs.getString(9) != null) {
                    b.setCheckInDate(LocalDate.parse(rs.getString(9)));
                } else {
                    b.setCheckInDate(null);
                }
                b.setPeriod(rs.getInt(10));
                b.setStatus(rs.getString(11));
                if (rs.getString(12) != null) {
                    b.setApproveUserID(Integer.parseInt(rs.getString(12)));
                } else {
                    b.setApproveUserID(0);
                }
                b.setSubmitUserName(queryUserNameByID(rs.getInt(2)));
                b.setApproveUserName(queryUserNameByID(rs.getInt(12)));
                b.setEquipmentName(queryEquipmentNameByID(rs.getInt(3)));
                arraylist.add(b);
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

    public boolean reduceEquipmentQtyByID(int id, int amount) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE Equipment SET stock=? WHERE equipment_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, (queryEquipmentQtyByID(id) - amount));
            pStmnt.setInt(2, id);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(id + " is updated");
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

    public int queryEquipmentQtyByID(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int amount = 0;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT stock FROM Equipment WHERE equipment_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            pStmnt.setInt(1, id);
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                amount = rs.getInt(1);
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
        return amount;
    }

    // edit user record
    public boolean editReservationStatusByID(int id, String status, int approveUserID, int equipmentID, int qty) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE Reservation SET status=?, approve_user_id=? WHERE reservation_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, status);
            if ("Rejected".equalsIgnoreCase(status)) {
                addEquipmentQtyByID(equipmentID, qty);
            }
            pStmnt.setInt(2, approveUserID);
            pStmnt.setInt(3, id);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(id + " is updated");
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

    public boolean addEquipmentQtyByID(int id, int amount) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE Equipment SET stock=? WHERE equipment_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, (queryEquipmentQtyByID(id) + amount));
            pStmnt.setInt(2, id);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(id + " is updated");
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

    public boolean checkOutByID(int id) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE Reservation SET status=?, checkout_date=? WHERE reservation_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, "Leasing");
            pStmnt.setObject(2, Date.valueOf(LocalDate.now()));
            pStmnt.setInt(3, id);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(id + " is updated");
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

    public ArrayList<ReservationBean> queryApprovedReservation() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<ReservationBean> arraylist = new ArrayList<ReservationBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Reservation WHERE status=\"Approved\"";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                ReservationBean b = new ReservationBean();
                b.setReservationID(rs.getInt(1));
                b.setSubmitUserID(rs.getInt(2));
                b.setEquipmentID(rs.getInt(3));
                b.setQty(rs.getInt(4));
                b.setRequestDate(LocalDate.parse(rs.getString(5)));
                b.setStartDate(LocalDate.parse(rs.getString(6)));
                b.setDueDate(LocalDate.parse(rs.getString(7)));
                if (rs.getString(8) != null) {
                    b.setCheckOutDate(LocalDate.parse(rs.getString(8)));
                } else {
                    b.setCheckOutDate(null);
                }
                if (rs.getString(9) != null) {
                    b.setCheckInDate(LocalDate.parse(rs.getString(9)));
                } else {
                    b.setCheckInDate(null);
                }
                b.setPeriod(rs.getInt(10));
                b.setStatus(rs.getString(11));
                if (rs.getString(12) != null) {
                    b.setApproveUserID(Integer.parseInt(rs.getString(12)));
                } else {
                    b.setApproveUserID(0);
                }
                b.setSubmitUserName(queryUserNameByID(rs.getInt(2)));
                b.setApproveUserName(queryUserNameByID(rs.getInt(12)));
                b.setEquipmentName(queryEquipmentNameByID(rs.getInt(3)));
                arraylist.add(b);
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

    public ArrayList<ReservationBean> queryLeasingReservation() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<ReservationBean> arraylist = new ArrayList<ReservationBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Reservation WHERE status=\"Leasing\"";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                ReservationBean b = new ReservationBean();
                b.setReservationID(rs.getInt(1));
                b.setSubmitUserID(rs.getInt(2));
                b.setEquipmentID(rs.getInt(3));
                b.setQty(rs.getInt(4));
                b.setRequestDate(LocalDate.parse(rs.getString(5)));
                b.setStartDate(LocalDate.parse(rs.getString(6)));
                b.setDueDate(LocalDate.parse(rs.getString(7)));
                if (rs.getString(8) != null) {
                    b.setCheckOutDate(LocalDate.parse(rs.getString(8)));
                } else {
                    b.setCheckOutDate(null);
                }
                if (rs.getString(9) != null) {
                    b.setCheckInDate(LocalDate.parse(rs.getString(9)));
                } else {
                    b.setCheckInDate(null);
                }
                b.setPeriod(rs.getInt(10));
                b.setStatus(rs.getString(11));
                if (rs.getString(12) != null) {
                    b.setApproveUserID(Integer.parseInt(rs.getString(12)));
                } else {
                    b.setApproveUserID(0);
                }
                b.setSubmitUserName(queryUserNameByID(rs.getInt(2)));
                b.setApproveUserName(queryUserNameByID(rs.getInt(12)));
                b.setEquipmentName(queryEquipmentNameByID(rs.getInt(3)));
                arraylist.add(b);
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

    public boolean checkInByID(int id, int equipmentID, int qty) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        boolean isSuccess = false;

        try {
            cnnct = getConnection();
            String preQueryStatement = "UPDATE Reservation SET status=?, checkin_date=? WHERE reservation_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setString(1, "Completed");
            pStmnt.setObject(2, Date.valueOf(LocalDate.now()));
            pStmnt.setInt(3, id);
            addEquipmentQtyByID(equipmentID, qty);
            int rowCount = pStmnt.executeUpdate();
            if (rowCount >= 1) {
                isSuccess = true;
                System.out.println(id + " is updated");
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

    public ArrayList<ReservationBean> queryRservationsByID(int stuID) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<ReservationBean> arraylist = new ArrayList<ReservationBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT * FROM Reservation WHERE submit_user_id=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setInt(1, stuID);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                ReservationBean b = new ReservationBean();
                b.setReservationID(rs.getInt(1));
                b.setSubmitUserID(rs.getInt(2));
                b.setEquipmentID(rs.getInt(3));
                b.setQty(rs.getInt(4));
                b.setRequestDate(LocalDate.parse(rs.getString(5)));
                b.setStartDate(LocalDate.parse(rs.getString(6)));
                b.setDueDate(LocalDate.parse(rs.getString(7)));
                if (rs.getString(8) != null) {
                    b.setCheckOutDate(LocalDate.parse(rs.getString(8)));
                } else {
                    b.setCheckOutDate(null);
                }
                if (rs.getString(9) != null) {
                    b.setCheckInDate(LocalDate.parse(rs.getString(9)));
                } else {
                    b.setCheckInDate(null);
                }
                b.setPeriod(rs.getInt(10));
                b.setStatus(rs.getString(11));
                if (rs.getString(12) != null) {
                    b.setApproveUserID(Integer.parseInt(rs.getString(12)));
                } else {
                    b.setApproveUserID(0);
                }
                b.setSubmitUserName(queryUserNameByID(rs.getInt(2)));
                b.setApproveUserName(queryUserNameByID(b.getApproveUserID()));
                b.setEquipmentName(queryEquipmentNameByID(rs.getInt(3)));
                arraylist.add(b);
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

    public ArrayList<ReservationBean> queryEquipmentUtilization() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<ReservationBean> arraylist = new ArrayList<ReservationBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT equipment_id, SUM(period) FROM Reservation WHERE NOT(status='Rejected') GROUP BY equipment_id";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                ReservationBean b = new ReservationBean();
                b.setEquipmentID(rs.getInt(1));
                b.setPeriod(rs.getInt(2));
                b.setEquipmentName(queryEquipmentNameByID(rs.getInt(1)));
                arraylist.add(b);
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

    public int queryAllPeriod() {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int period = 0;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT SUM(period) FROM Reservation WHERE NOT(status='Rejected')";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                period = rs.getInt(1);
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
        return period;
    }

    public ArrayList<ReservationBean> queryEquipmentUtilizationByDate(LocalDate startDate, LocalDate endDate) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;

        ArrayList<ReservationBean> arraylist = new ArrayList<ReservationBean>();
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT equipment_id, SUM(period) FROM Reservation WHERE NOT(status='Rejected') AND start_date>=? and due_date<=? GROUP BY equipment_id";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setObject(1, Date.valueOf(startDate));
            pStmnt.setObject(2, Date.valueOf(endDate));
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            while (rs.next()) {
                ReservationBean b = new ReservationBean();
                b.setEquipmentID(rs.getInt(1));
                b.setPeriod(rs.getInt(2));
                b.setEquipmentName(queryEquipmentNameByID(rs.getInt(1)));
                arraylist.add(b);
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

    public int queryPeriodByDate(LocalDate startDate, LocalDate endDate) {
        Connection cnnct = null;
        PreparedStatement pStmnt = null;
        int period = 0;
        try {
            cnnct = getConnection();
            String preQueryStatement = "SELECT SUM(period) FROM Reservation WHERE NOT(status='Rejected') AND start_date>=? and due_date<=?";
            pStmnt = cnnct.prepareStatement(preQueryStatement);
            pStmnt.setObject(1, Date.valueOf(startDate));
            pStmnt.setObject(2, Date.valueOf(endDate));
            ResultSet rs = null;
            rs = pStmnt.executeQuery();
            if (rs.next()) {
                period = rs.getInt(1);
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
        return period;
    }
}
