package com.cursor.entry;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Developer {
    public static void main(String[] args) {
        DeveloperEntry de = new DeveloperEntry();
        de.insertRecords();
        de.closeResources();
    }
}

class DeveloperEntry {

    String driver = "jdbc:mysql://localhost:3306/company";
    String user = "root";
    String password = "";
    Connection con = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    DeveloperEntry() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(driver, user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load driver class");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Unable to connect to database");
            e.printStackTrace();
        }
    }

    String sql = "call developer_entry(?,?,?)";

    String name = null;
    String designation = null;
    int salary = 0;

    public void setName(String name) {
        this.name = name;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    void insertRecords() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name");
        setName(sc.nextLine());
        System.out.println("Enter designation");
        setDesignation(sc.nextLine());
        System.out.println("Enter salary");
        try {
            setSalary(sc.nextInt());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer for salary.");
            sc.close();
            return;
        }
        sc.close();
        try {
            stmt = con.prepareCall(sql);
            stmt.setString(1, name);
            stmt.setString(2, designation);
            stmt.setInt(3, salary);
            stmt.execute();
            System.out.println("Record inserted successfully");
        } catch (SQLException e) {
            System.out.println("Unable to insert record");
            e.printStackTrace();
        }
    }

    void closeResources() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Unable to close resources");
            e.printStackTrace();
        }
    }
}
