package com.iradann.newsclassification.controllers;

import com.iradann.newsclassification.models.ClassificationForm;
import com.iradann.newsclassification.models.News;
import com.iradann.newsclassification.models.NewsCategory;
import com.iradann.newsclassification.models.TextClass;
import com.iradann.newsclassification.services.NewsService;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewsController {
    
    private NewsService newsService;
    private NewsCategory[] categories;
    
    public NewsController(){
        newsService = new NewsService();
        
        categories = new NewsCategory[TextClass.values().length + 1];
        categories[0] = new NewsCategory("/news/list", "Все новости");
        for (int i = 0; i < TextClass.values().length; ++i) {
            categories[1 + i] = new NewsCategory("/news/list?category=" + TextClass.values()[i].toString().toLowerCase(), TextClass.translateTextClassToRus(TextClass.values()[i]));
        }
        
    }
    
    @RequestMapping(value = "/news/list", method = RequestMethod.GET)
    public String newsList(Model model, @RequestParam(value="category", required=false, defaultValue = "") String category) {
        model.addAttribute("categories", categories);
        
        TextClass textClass = null;
        try {
            textClass = TextClass.valueOf(category.toUpperCase());
        } catch (Exception ex) { }
        
        if (textClass != null) {
            model.addAttribute("selectedCategory", "Новости по категории: " + TextClass.translateTextClassToRus(textClass));
            ArrayList<News> news = newsService.getNewsList(textClass);
            model.addAttribute("newsList", news);
            model.addAttribute("newsCount", news.size());
        } else {
            model.addAttribute("selectedCategory", "Все новости");
            ArrayList<News> news = newsService.getAllNews();
            model.addAttribute("newsList", news);
            model.addAttribute("newsCount", news.size());
        }
        
        return "NewsList";
    }
    
    @RequestMapping(value = "/news/id/{newsId}", method = RequestMethod.GET)
    public String news(Model model, @PathVariable("newsId") Integer newsId) {
        model.addAttribute("news", newsService.getNews(newsId));
        return "NewsDetails";
    }
}
