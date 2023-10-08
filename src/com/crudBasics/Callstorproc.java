package com.crudBasics;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Scanner;

class Storproc {
    String driver = "jdbc:mysql://localhost:3306/flat2dbms";
    String user = "root";
    String password = "";
    Connection con = null;
    CallableStatement stmt = null;
    ResultSet rs = null;

    int id;
    String name = new String();
    String actors = new String();
    String dor = new String();
    String genre = new String();
    String director = new String();
    String boxcollection = new String();

    String displaString = "{call display()}";
    String insertSql = "{call insertWithParam(?,?,?,?,?,?)}";
    String deleteSql = "{call deleteWithParams(?)}";
    String updateSql = "{call updateWithParams(?,?,?,?,?,?,?)}";

    Storproc() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(driver, user, password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void displayRecords() {
        try {

            stmt = con.prepareCall(displaString);
            rs = stmt.executeQuery();

            String[] fields = { "id", "Name", "Actors", "DOR", "Genre", "Director", "BoxCollection" };
            int rowCount = 0;
            while (rs.next()) {
                System.out.println("------------------------------");
                System.out.println("Row " + (++rowCount) + ":\n");
                for (String field : fields) {
                    String value = rs.getString(field.toLowerCase()); // Assuming column names are lowercase
                    System.out.println(field + ": " + value);
                }
                System.out.println("------------------------------");
            }
            rowCount = 0;

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void inputRecords() {
        try {
            stmt = con.prepareCall(insertSql);

            try (Scanner sc = new Scanner(System.in)) {
                System.out.print("Enter movie name : ");
                name = sc.nextLine();
                System.out.print("Enter actors name : ");
                actors = sc.nextLine();
                System.out.print("Enter date of release : ");
                dor = sc.nextLine();
                System.out.print("Enter genre : ");
                genre = sc.nextLine();
                System.out.print("Enter Director : ");
                director = sc.nextLine();
                System.out.print("Enter Box office collection : ");
                boxcollection = sc.nextLine();
            }
            stmt.setString(1, name);
            stmt.setString(2, actors);
            stmt.setString(3, dor);
            stmt.setString(4, genre);
            stmt.setString(5, director);
            stmt.setString(6, boxcollection);
            int count = stmt.executeUpdate();
            if (count > 0) {
                System.out.println("Record inserted successfully");
            } else {
                System.out.println("Record insertion failed");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void deleteRecords() {
        try {
            stmt = con.prepareCall(deleteSql);
            try (Scanner sc = new Scanner(System.in)) {
                System.out.print("Enter movie id to delete : ");
                id = sc.nextInt();
            }
            stmt.setInt(1, id);

            int count = stmt.executeUpdate();
            if (count > 0) {
                System.out.println("Record deleted successfully::: No of records deleted : " + count + "");
            } else {
                System.out.println("Record deletion failed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void updateRecords() {
        try {
            stmt = con.prepareCall(updateSql);
            try (Scanner sc = new Scanner(System.in)) {
                System.out.print("Enter movie id to update : ");
                id = sc.nextInt();
                System.out.print("Enter movie name : ");
                name = sc.nextLine();
                name = sc.nextLine();
                System.out.print("Enter actors name : ");
                actors = sc.nextLine();
                System.out.print("Enter date of release : ");
                dor = sc.nextLine();
                System.out.print("Enter genre : ");
                genre = sc.nextLine();
                System.out.print("Enter Director : ");
                director = sc.nextLine();
                System.out.print("Enter Box office collection : ");
                boxcollection = sc.nextLine();
            }
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, actors);
            stmt.setString(4, dor);
            stmt.setString(5, genre);
            stmt.setString(6, director);
            stmt.setString(7, boxcollection);
            int count = stmt.executeUpdate();
            if (count > 0) {
                System.out.println("Record updated successfully::: No of records updated : " + count + "");
            } else {
                System.out.println("Record updation failed");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    void showHit() throws SQLException {
        stmt = con.prepareCall("{call showHit}");
        rs = stmt.executeQuery();
        while (rs.next())
            System.out.println(rs.getString("name"));

    }

    void showFlop() throws SQLException {
        stmt = con.prepareCall("{call showFlop}");
        rs = stmt.executeQuery();
        while (rs.next())
            System.out.println(rs.getString("name"));

    }
}

class Callstorproc {
    public static void main(String[] args) throws SQLException {
        Storproc sp = new Storproc();
        sp.showHit();
        System.out.println();
        sp.showFlop();
    }
}
