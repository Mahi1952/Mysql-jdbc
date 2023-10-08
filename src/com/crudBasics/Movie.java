package com.crudBasics;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class Movie {
	private String name = "";
	private String actors = "";
	private String dor = "";
	private String genre = "";
	private String director = "";
	private String boxcollection = "";
	private String exported = "";

	private final String driver = "jdbc:mysql://localhost:3306/flat2dbms";
	private final String user = "root";
	private final String password = "";
	private Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;

	Movie() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(driver, user, password);
			// here sonoo is the database name, root is the username and password
			stmt = con.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	int size() {
		return (2 * (30 + 60 + 20 + 15 + 20 + 8 + 1));
	}

	String getname() {
		return name;
	}

	String getactors() {
		return actors;
	}

	String getdor() {
		return dor;
	}

	String getgenre() {
		return genre;
	}

	String getdirector() {
		return director;
	}

	String getboxcollection() {
		return boxcollection;
	}

	String getexported() {
		return exported;
	}

	void feeddata(String name, String actors, String dor, String genre, String director, String boxcollection,
			String exported) {
		this.name = name;
		this.actors = actors;
		this.dor = dor;
		this.genre = genre;
		this.director = director;
		this.boxcollection = boxcollection;
		this.exported = exported;
	}

	void printdata() {
		System.out.println("Movie name: " + name);
		System.out.println("Actors: " + actors);
		System.out.println("Date of release: " + dor);
		System.out.println("Genre: " + genre);
		System.out.println("Director: " + director);
		System.out.println("Box office Collection: " + boxcollection);
		System.out.println("Exported: " + exported);
		System.out.println("----------------------------------------------------------");
	}

	void write(RandomAccessFile raf) throws IOException {
		StringBuffer temp;
		temp = new StringBuffer(name);
		temp.setLength(30);
		raf.writeChars(temp.toString());

		temp = new StringBuffer(actors);
		temp.setLength(60);
		raf.writeChars(temp.toString());

		temp = new StringBuffer(dor);
		temp.setLength(20);
		raf.writeChars(temp.toString());

		temp = new StringBuffer(genre);
		temp.setLength(15);
		raf.writeChars(temp.toString());

		temp = new StringBuffer(director);
		temp.setLength(20);
		raf.writeChars(temp.toString());

		temp = new StringBuffer(boxcollection);
		temp.setLength(8);
		raf.writeChars(temp.toString());

		temp = new StringBuffer(exported);
		temp.setLength(1);
		raf.writeChars(temp.toString());
	}

	void read(RandomAccessFile raf) throws IOException {
		char[] temp = new char[30];
		for (int i = 0; i < temp.length; i++)
			temp[i] = raf.readChar();
		name = new String(temp);

		temp = new char[60];
		for (int i = 0; i < temp.length; i++)
			temp[i] = raf.readChar();
		actors = new String(temp);

		temp = new char[20];
		for (int i = 0; i < temp.length; i++)
			temp[i] = raf.readChar();
		dor = new String(temp);

		temp = new char[15];
		for (int i = 0; i < temp.length; i++)
			temp[i] = raf.readChar();
		genre = new String(temp);

		temp = new char[20];
		for (int i = 0; i < temp.length; i++)
			temp[i] = raf.readChar();
		director = new String(temp);

		temp = new char[8];
		for (int i = 0; i < temp.length; i++)
			temp[i] = raf.readChar();
		boxcollection = new String(temp);

		temp = new char[1];
		for (int i = 0; i < temp.length; i++)
			temp[i] = raf.readChar();
		exported = new String(temp);
	}

	void readdata() {
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
		exported = "0";
	}

	void insertRecord() throws SQLException {
		String sql = "INSERT INTO movie (name, actors, dor, genre, director, boxcollection) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, name.trim());
			pstmt.setString(2, actors.trim());
			pstmt.setString(3, dor.trim());
			pstmt.setString(4, genre.trim());
			pstmt.setString(5, director.trim());
			pstmt.setString(6, boxcollection.trim());
			int n = pstmt.executeUpdate();
			if (n == 1)
				System.out.println("Record inserted Successfully");
			else
				System.out.println("Record insertion failed");
		}
	}

	void importData() throws IOException {
		RandomAccessFile raf = new RandomAccessFile("movie.dat", "rw");
		raf.seek(0);

		int num = (int) raf.length() / size();
		for (int i = 0; i < num; i++) {
			read(raf);
			System.out.println("Record number: " + (i + 1));
			printdata();
			try {
				if (exported.equals("0")) {
					insertRecord();
					exported = "1";
					raf.seek(raf.getFilePointer() - size());
					write(raf);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		raf.close();
	}

	void exportData() throws IOException, SQLException {

		RandomAccessFile raf = new RandomAccessFile("movie.dat", "rw");
		raf.seek((int) raf.length());

		try {
			rs = stmt.executeQuery("select * from movie");
			while (rs.next()) {
				// id = rs.getInt("id");
				name = rs.getString("name");
				actors = rs.getString("actors");
				dor = rs.getString("dor");
				genre = rs.getString("genre");
				director = rs.getString("director");
				boxcollection = rs.getString("boxcollection");

				System.out.println("Name : " + getname());
				System.out.println("Actor Name : " + getactors());
				System.out.println("Date Of Release : " + getdor());
				System.out.println("Genre : " + getgenre());
				System.out.println("Director :" + getdirector());
				System.out.println("Box Office : " + getboxcollection());

				System.out.println("---------------------------------------------------");

				write(raf);
			}
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	void readrecords() throws SQLException {
		try {
			rs = stmt.executeQuery("select * from movie");
			while (rs.next()) {
				// id = rs.getInt("id");
				name = rs.getString("name");
				actors = rs.getString("actor");
				dor = rs.getString("dor");
				genre = rs.getString("genre");
				director = rs.getString("director");
				boxcollection = rs.getString("boxcollection");

				System.out.println("Name : " + name);
				System.out.println("Actor Name : " + actors);
				System.out.println("Date Of Release : " + dor);
				System.out.println("Genre : " + genre);
				System.out.println("Director :" + director);
				System.out.println("Box Office : " + boxcollection);

				System.out.println("---------------------------------------------------");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
