package com.iradann.newsclassification;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class SQLiteDatabaseDriver {
    
    public static HashMap<String, Integer> getVectorWord() {
        HashMap<String, Integer> vectorWords = new HashMap<>();
        
        try (Connection conn = connect();
            PreparedStatement pstmt  = conn.prepareStatement(
                    "SELECT Word, [Order] FROM VectorWord"
            )){
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                vectorWords.put(rs.getString(1), rs.getInt(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return vectorWords;
    }
    
    public static ArrayList<String> getFilteredWords() {
        ArrayList<String> filteredWords = new ArrayList<>();
        
        try (Connection conn = connect();
            PreparedStatement pstmt  = conn.prepareStatement(
                    "SELECT Word FROM FilteredWord"
            )){
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                filteredWords.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return filteredWords;
    }
    
    private static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:ClassifierDB.db";
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}