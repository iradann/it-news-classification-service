package com.iradann.newsclassification.models;

public class News {
    private Integer id;
    private TextClass textClass;
    private String title;
    private String article;
    
    
    public News(Integer id, TextClass textClass, String title, String article) {
        this.id = id;
        this.textClass = textClass;
        this.title = title;
        this.article = article;
    }
    
    public Integer getId() {
        return id;
    }
    
    public TextClass getTextClass() {
        return textClass;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getArticle() {
        return article;
    }
}
