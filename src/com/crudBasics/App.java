package com.crudBasics;

public class App {
    public static void main(String[] args) {
        Movie movie = new Movie();
        try {
            // movie.importData();

            movie.exportData();
        } catch (Exception e) {
            System.out.println(e);
        }
        StringBuilder sb = null;
        sb.reverse();
    }
}
