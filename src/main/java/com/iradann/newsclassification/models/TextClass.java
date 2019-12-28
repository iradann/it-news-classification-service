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
    
    public static String translateTextClassToRus(TextClass textClass) {
        switch (textClass) {
            case BUSINESS:
                return "Бизнес и технологии";
            case COMMON_NEWS:
                return "Общие новости";
            case DESIGN:
                return "Дизайн";
            case FINANCE:
                return "Финансы";
            case MARKETING:
                return "Маркетинг";
            case QUESTION_ANSWER:
                return "Вопрос - ответ";
            case SOCIETY:
                return "Соц сети и сервисы";
            case TRANSPORT:
                return "Транспорт";
            case WORK_SEARCHING:
                return "Поиск работы";
            default:
                return "";
        }
    }
}
