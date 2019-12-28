package com.iradann.newsclassification.services;

import com.iradann.newsclassification.SQLiteDatabaseDriver;
import com.iradann.newsclassification.models.News;
import com.iradann.newsclassification.models.TextClass;
import java.util.ArrayList;

public class NewsService {
    private SQLiteDatabaseDriver databaseDriver;
    
    public NewsService(){
        databaseDriver = new SQLiteDatabaseDriver();
    }
    
    public ArrayList<News> getAllNews() {
        return databaseDriver.getAllNews();
    }
    
    public ArrayList<News> getNewsList(TextClass textClass) {
        return databaseDriver.getNewsByCategory(textClass);
    }
    
    public News getNews(int articleId) {
        return databaseDriver.getNews(articleId);
    }
}
