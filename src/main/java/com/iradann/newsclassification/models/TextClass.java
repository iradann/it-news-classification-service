package com.iradann.newsclassification.models;

public enum TextClass {
    BUSINESS(0),
    COMMON_NEWS(1),
    DESIGN(2),
    FINANCE(3),
    MARKETING(4),
    QUESTION_ANSWER(5),
    SOCIETY(6),
    TRANSPORT(7),
    WORK_SEARCHING(8);

    private int id;
    
    TextClass(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public static TextClass getTextClassById(int id) {
        for (TextClass textClass: TextClass.values()) {
            if (textClass.getId() == id) {
                return textClass;
            }
        }
        return null;
    }
}
