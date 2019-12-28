package com.iradann.newsclassification.controllers;

import com.iradann.newsclassification.models.ClassificationForm;
import com.iradann.newsclassification.models.TextClass;
import com.iradann.newsclassification.services.ClassificationService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ClassificationController {
    
    private ClassificationService classificationService;
    
    public ClassificationController() throws Exception {
        classificationService = new ClassificationService();
    }
    
    @RequestMapping(value = "/classification", method = RequestMethod.GET)
    public String classificationForm(Model model) {
        model.addAttribute("classificationForm", new ClassificationForm());
        return "ClassificationForm";
    }
    
    @RequestMapping(value = "/classification", method = RequestMethod.POST)
    public String classificationResult(@ModelAttribute ClassificationForm classificatorForm, Model model) throws Exception {
        TextClass textClass = classificationService.getClass(classificatorForm.getText());
        model.addAttribute("textClass", TextClass.translateTextClassToRus(textClass));
        return "ClassificationResult";
    }
}
