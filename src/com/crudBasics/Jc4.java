package com.crudBasics;

import java.util.Scanner;
import java.sql.*;
import java.sql.PreparedStatement;

class FlatOwner {
    int id;
    String flat_no = new String();
    String owner_name = new String();
    String phone = new String();
    String email = new String();
    int no_of_members;
    int tower_id;

    String driver = "jdbc:mysql://localhost:3306/hub";
    String user = "root";
    String password = "";
    Connection con = null;
    Statement stmt = null;
    ResultSet rs = null;

    FlatOwner() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(driver, user, password);
            // here sonoo is database name, root is username and password
            stmt = con.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void inputRecords() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter Flat Number : ");
            flat_no = sc.nextLine();
            System.out.print("Enter Owner Name : ");
            owner_name = sc.nextLine();
            System.out.print("Enter Phone Number : ");
            phone = sc.nextLine();
            System.out.print("Enter Email : ");
            email = sc.nextLine();
            System.out.print("Enter number of members : ");
            no_of_members = sc.nextInt();
            System.out.print("Enter Tower ID : ");
            tower_id = sc.nextInt();
        }
    }

    void readRecords() throws SQLException {
        try {
            rs = stmt.executeQuery("select * from flat_owner");
            while (rs.next()) {
                id = rs.getInt("id");
                flat_no = rs.getString("flat_no");
                owner_name = rs.getString("owner_name");
                phone = rs.getString("phone");
                email = rs.getString("email");
                no_of_members = rs.getInt("no_of_members");
                tower_id = rs.getInt("tower_id");

                System.out.println("id : " + id);
                System.out.println("flat_no : " + flat_no);
                System.out.println("owner_name : " + owner_name);
                System.out.println("phone : " + phone);
                System.out.println("email : " + email);
                System.out.println("no_of_members : " + no_of_members);
                System.out.println("tower_id : " + tower_id);
                System.out.println("---------------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void readOneRecord(String tid) throws SQLException {
        try {
            rs = stmt.executeQuery("select * from flat_owner where id = " + tid);
            rs.next();
            id = rs.getInt("id");
            flat_no = rs.getString("flat_no");
            owner_name = rs.getString("owner_name");
            phone = rs.getString("phone");
            email = rs.getString("email");
            no_of_members = rs.getInt("no_of_members");
            tower_id = rs.getInt("tower_id");

            System.out.println("---------------------------------------------------");
            System.out.println("id : " + id);
            System.out.println("flat_no : " + flat_no);
            System.out.println("owner_name : " + owner_name);
            System.out.println("phone : " + phone);
            System.out.println("email : " + email);
            System.out.println("no_of_members : " + no_of_members);
            System.out.println("tower_id : " + tower_id);
            System.out.println("---------------------------------------------------");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void updateRecords() throws SQLException {
        int n;
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter the record id to be updated : ");
            String id = sc.nextLine();
            readOneRecord(id);
            inputRecords();
            String sql = "update flat_owner set flat_no = " + flat_no + ", owner_name = '" + owner_name + "', phone = '"
                    + phone + "', email = '" + email + "', no_of_members = " + no_of_members + ", tower_id = "
                    + tower_id + " where id = " + id;
            n = stmt.executeUpdate(sql);
        }
        System.out.println(n + " record updated");
    }

    void updateRecordsv2() throws SQLException {
        int n;
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter the record id to be updated : ");
            String id = sc.nextLine();
            readOneRecord(id);
            inputRecords();
            String sql = "update flat_owner set flat_no = ?, owner_name = ?, phone = ?, email = ?, no_of_members = ?, tower_id = ? where id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, flat_no);
            pstmt.setString(2, owner_name);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            pstmt.setInt(5, no_of_members);
            pstmt.setInt(6, tower_id);
            pstmt.setString(7, id);

            n = pstmt.executeUpdate();
        }
        System.out.println(n + " record updated");
    }

    void insertRecords() throws SQLException {
        int n;
        inputRecords();
        String sql = "insert into flat_owner (flat_no, owner_name, phone, email, no_of_members, tower_id) values ("
                + flat_no + ", '" + owner_name + "', '" + phone + "', '" + email + "', " + no_of_members + ", "
                + tower_id + ")";
        n = stmt.executeUpdate(sql);
        if (n == 1)
            System.out.println("Record inserted Successfully");
        else
            System.out.println("Record insertion failed");
    }

    void insertRecordsv2() throws SQLException {
        int n;
        inputRecords();

        String sql = "insert into flat_owner (flat_no, owner_name, phone, email, no_of_members, tower_id) "
                + "values (?,?,?,?,?,?)";

        PreparedStatement pstmt = con.prepareStatement(sql);

        pstmt.setString(1, flat_no);
        pstmt.setString(2, owner_name);
        pstmt.setString(3, phone);
        pstmt.setString(4, email);
        pstmt.setInt(5, no_of_members);
        pstmt.setInt(6, tower_id);

        n = pstmt.executeUpdate();

        if (n == 1)
            System.out.println("Record inserted Successfully");
        else
            System.out.println("Record insertion failed");
    }

    void deleteRecords() throws SQLException {
        int n;
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter the record id to be deleted : ");
            String id = sc.nextLine();
            String sql = "delete from flat_owner where id = " + id;
            n = stmt.executeUpdate(sql);
        }
        System.out.println(n + " record deleted");
    }

    void manipulate() throws Exception {
        rs = stmt.executeQuery("Select * from flat_owner");

        rs.absolute(5);
        rs.deleteRow();
        rs.moveToInsertRow();
        rs.updateInt("flat_no", 317);
        rs.insertRow();
        con.commit();
    }
}

class Jc4 {
    public static void main(String[] args) {
        FlatOwner fo = new FlatOwner();
        // try (Scanner sc = new Scanner(System.in)) {
        // int ch;

        // System.out.println("1. Insert Records");
        // System.out.println("2. Display Records");
        // System.out.println("3. Delete Records");
        // System.out.println("4. Update Records");
        // System.out.println("5. Exit");

        // System.out.print("Enter your choice : ");
        // ch = sc.nextInt();

        // try {
        // switch (ch) {
        // case 1:
        // // fo.insertrecords();
        // fo.insertRecordsv2();
        // break;
        // case 2:
        // fo.readRecords();
        // break;
        // case 3:
        // fo.deleteRecords();
        // break;
        // case 4:
        // // fo.updaterecord();
        // fo.updateRecordsv2();
        // break;
        // }
        // } catch (SQLException e) {
        // System.out.println(e);
        // }
        // }
        try {
            fo.manipulate();
        } catch (Exception e) {

        }
    }
}