package com.iradann.newsclassification;

import com.iradann.newsclassification.models.News;
import com.iradann.newsclassification.models.TextClass;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class SQLiteDatabaseDriver {
    
    public HashMap<String, Integer> getVectorWord() {
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
    
    public ArrayList<String> getFilteredWords() {
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
    
    public ArrayList<News> getAllNews() {
        ArrayList<News> newsList = new ArrayList<>();
        
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT Id, TextClassId, Title, Article FROM News")
            ){
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                newsList.add(new News(rs.getInt(1), TextClass.getTextClassById(rs.getInt(2)), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return newsList;
    }
    
    public ArrayList<News> getNewsByCategory(TextClass textClass) {
        ArrayList<News> newsList = new ArrayList<>();
        
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(
                    "SELECT Id, TextClassId, Title, Article FROM News WHERE TextClassId = ?")
            ){
            
            pstmt.setInt(1, textClass.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                newsList.add(new News(rs.getInt(1), TextClass.getTextClassById(rs.getInt(2)), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return newsList;
    }
    
    public News getNews(int articleId) {
        News news = null;
        
        try (Connection conn = connect();
            PreparedStatement pstmt  = conn.prepareStatement(
                    "SELECT Id, TextClassId, Title, Article FROM News WHERE Id = ?"
            )){
            
            pstmt.setInt(1, articleId);
            
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            news = new News(rs.getInt(1), TextClass.getTextClassById(rs.getInt(2)), rs.getString(3), rs.getString(4));
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return news;
    }
    
    private Connection connect() {
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