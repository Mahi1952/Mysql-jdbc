package com.crudBasics;

import java.io.*;
import java.util.Scanner;
import java.io.IOException;
import java.io.RandomAccessFile;

class DATcontrol {
	public static void main(String[] args) throws IOException {
		RandomAccessFile raf = new RandomAccessFile("movie.dat", "rw");
		RandomAccessFile traf = new RandomAccessFile("temp.dat", "rw");

		Movie m = new Movie();
		int num = (int) raf.length() / m.size();
		try (Scanner in = new Scanner(System.in)) {
			int ch, rec;

			System.out.println("1.Add records");
			System.out.println("2.List records");
			System.out.println("3.Delete records");
			System.out.println("4.Modify records");
			System.out.print("Please enter your choice : ");
			ch = in.nextInt();
			switch (ch) {
				case 1:
					raf.seek((int) raf.length());
					m.readdata();
					m.write(raf);
					break;
				case 2:
					raf.seek(0);
					for (int i = 0; i < num; i++) {
						m.read(raf);
						System.out.println("Record number : " + (i + 1));
						m.printdata();
					}
					break;
				case 3:
					System.out.print("Enter the record to be deleted : ");
					rec = in.nextInt();
					raf.seek(0);
					for (int i = 0; i < num; i++) {
						m.read(raf);
						if (rec != (i + 1)) {
							m.write(traf);
						}
					}
					traf.close();
					raf.close();
					File f = new File("temp.dat");
					f.renameTo(new File("movie.dat"));
					break;
				case 4:
					System.out.println("Enter the record number to be Modified : ");
					rec = in.nextInt();
					rec = in.nextInt();
					raf.seek((rec - 1) * m.size());
					m.read(raf);
					System.out.println("The OLD values..");
					m.printdata();
					System.out.println("Please modify the OLD values..");
					m.readdata();
					raf.seek(raf.getFilePointer() - m.size());
					m.write(raf);
					break;
			}
		}

	}

}