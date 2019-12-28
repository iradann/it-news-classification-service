package com.iradann.newsclassification.models;

public class NewsCategory {
    private String url;
    private String name;
    
    public NewsCategory(String url, String name) {
        this.url = url;
        this.name = name;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getName() {
        return name;
    }
}
