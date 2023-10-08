package com.cursor.entry;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ProjectManager {
    public static void main(String[] args) throws SQLException {
        ProjectManagerEntry pe = new ProjectManagerEntry();
        pe.insertRecords();
        pe.closeResources();
    }
}

class ProjectManagerEntry {

    String driver = "jdbc:mysql://localhost:3306/company";
    String user = "root";
    String password = "";
    Connection con = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    ProjectManagerEntry() {
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

    String sql = "call project_manager_entry(?,?,?,?,?,?)";

    int id = 0;
    String name = null;
    String email = null;
    boolean isCertified = false;
    String dateOfJoin = null;
    int salary = 0;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public void setIsCertified(boolean isCertified) {
        this.isCertified = isCertified;
    }

    public void setDateOfJoin(String dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    void insertRecords() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter id");
        setId(sc.nextInt());
        sc.nextLine(); // consume the newline character left by nextInt()
        System.out.println("Enter name");
        setName(sc.nextLine());
        System.out.println("Enter email");
        setEmail(sc.nextLine());
        System.out.println("Enter isCertified (true/false)");
        String isCertifiedInput = sc.nextLine().toLowerCase();
        setIsCertified(
                isCertifiedInput.equals("yes") || isCertifiedInput.equals("1") || isCertifiedInput.equals("assertive"));
        System.out.println("Enter dateOfJoin (yyyy-mm-dd)");
        setDateOfJoin(sc.nextLine());
        System.out.println("Enter salary");
        setSalary(sc.nextInt());
        sc.close();
        try {
            stmt = con.prepareCall(sql);
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setInt(4, isCertified ? 1 : 0);
            stmt.setString(5, dateOfJoin);
            stmt.setInt(6, salary);
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
