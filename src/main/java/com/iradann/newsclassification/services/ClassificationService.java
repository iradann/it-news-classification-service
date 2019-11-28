package com.iradann.newsclassification.services;

import com.iradann.newsclassification.SQLiteDatabaseDriver;
import com.iradann.newsclassification.models.TextClass;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.SerializationHelper;

public class ClassificationService {
    private Classifier classifier;
    private HashMap<String, Integer> vectorWords;
    private ArrayList<String> filteredWords;
    
    public ClassificationService() throws Exception {
        classifier = (Classifier) SerializationHelper.read(new FileInputStream("Classifier.model"));
        
        vectorWords = SQLiteDatabaseDriver.getVectorWord();
        filteredWords = SQLiteDatabaseDriver.getFilteredWords();
    }
    
    public TextClass getClass(String text) throws Exception {
        Instances vectorizedText = vectorize(text);
        
        double textClass = classifier.classifyInstance(vectorizedText.firstInstance());
        
        return TextClass.getTextClassById((int)textClass);
    }
    
    private Instances vectorize(String text) {
        ArrayList<String> words = new ArrayList<>();
        
        String[] uncheckedWords = text.split("\\s+");
        for (int i = 0; i < uncheckedWords.length; ++i) {
            String checkedWord = normalize(uncheckedWords[i]);
            if (!checkedWord.isEmpty() || filteredWords.contains(checkedWord)) {
                words.add(checkedWord);
            }
        }
        
        double[] vector = new double[vectorWords.size() + 1];
        
        for (int i = 0; i < vector.length; ++i) {
            vector[i] = 0;
        }
        
        for (int i = 0; i < words.size(); ++i) {
            Integer attributeIndex = vectorWords.get(words.get(i));
            if (attributeIndex != null) {
                vector[attributeIndex] = vector[attributeIndex] + 1;
            }
        }
        
        ArrayList<String> textClasses = new ArrayList<>();
        for (int i = 0; i < TextClass.values().length; ++i) {
            textClasses.add(TextClass.values()[i].toString().toLowerCase());
        }
        Attribute classAttribute = new Attribute("TextClass", textClasses);
        
        ArrayList<Attribute> wordAttributes = new ArrayList<>();
        for (int i = 0; i < vectorWords.size(); ++i) {
            wordAttributes.add(new Attribute("AttributeWord" + (i + 1)));
        }
        wordAttributes.add(classAttribute);
        
        Instances vectorizedText;
        vectorizedText = new Instances("Text", wordAttributes, 0);
        vectorizedText.setClass(classAttribute);
        vectorizedText.add(new DenseInstance(1.0, vector));
        vectorizedText.instance(0).setClassMissing();
        return vectorizedText;
    }
    
    private String normalize(String word) {
        return word.toLowerCase().replaceAll("[.,\\/#!\\?$%\\^&\\*;:{}=_`~()]", "").trim();
    }
}
