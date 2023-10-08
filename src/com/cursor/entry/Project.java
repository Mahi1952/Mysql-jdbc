package com.cursor.entry;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Project {
    public static void main(String[] args) throws SQLException {
        ProjectEntry pe = new ProjectEntry();
        pe.insertRecords();
        pe.closeResources();
    }
}

class ProjectEntry {

    String driver = "jdbc:mysql://localhost:3306/company";
    String user = "root";
    String password = "";
    Connection con = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    ProjectEntry() {
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

    String sql = "call project_entry(?,?,?,?,?)";

    String name = null;
    String description = null;
    String deadline = null;
    String sow = null;
    String budget = null;

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setSow(String sow) {
        this.sow = sow;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    void insertRecords() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name");
        setName(sc.nextLine());
        System.out.println("Enter description");
        setDescription(sc.nextLine());
        System.out.println("Enter deadline");
        setDeadline(sc.nextLine());
        System.out.println("Enter sow");
        setSow(sc.nextLine());
        System.out.println("Enter budget");
        setBudget(sc.nextLine());
        sc.close();
        try {
            stmt = con.prepareCall(sql);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, deadline);
            stmt.setString(4, sow);
            stmt.setString(5, budget);
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
