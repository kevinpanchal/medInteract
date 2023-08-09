package com.csci5308.medinteract.article.service;

import com.csci5308.medinteract.article.model.ArticleModel;
import com.csci5308.medinteract.article.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService{
    ArticleRepository articleRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository){
        this.articleRepository = articleRepository;
    }

    @Override
    public ArticleModel saveArticle(ArticleModel articleModel) {
        return articleRepository.save(articleModel);
    }

    @Override
    public List<Map<String, Object>> fetchAllArticles() {
        List<Object> articles = articleRepository.findAllArticles();
        List<Map<String, Object>> articleList = new ArrayList<>();
        for(int i =0; i <articles.size(); i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("id",(Long)(((Object[])articles.get(i))[0]));
            data.put("createdDate",(LocalDateTime)((Object[])articles.get(i))[1]);
            data.put("doctorId",(Long)(((Object[])articles.get(i))[2]));
            data.put("title",(String)(((Object[])articles.get(i))[3]));
            data.put("coverImage",(String)(((Object[])articles.get(i))[4]));
            data.put("doctorName",(String)(((Object[])articles.get(i))[5]));
            data.put("profilePicture",(String)(((Object[])articles.get(i))[6]));
            articleList.add(data);
        }
        return articleList;
    }

    @Override
    public List<Map<String, Object>> fetchAllDoctorArticles(Long id) {
        List<Object> articles = articleRepository.findAllDoctorArticles(id);
        List<Map<String, Object>> articleList = new ArrayList<>();
        for(int i =0; i <articles.size(); i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("id",(Long)(((Object[])articles.get(i))[0]));
            data.put("createdDate",(LocalDateTime)((Object[])articles.get(i))[1]);
            data.put("doctorId",(Long)(((Object[])articles.get(i))[2]));
            data.put("title",(String)(((Object[])articles.get(i))[3]));
            data.put("coverImage",(String)(((Object[])articles.get(i))[4]));
            data.put("doctorName",(String)(((Object[])articles.get(i))[5]));
            data.put("profilePicture",(String)(((Object[])articles.get(i))[6]));
            articleList.add(data);
        }
        return articleList;
    }

    @Override
    public List<Map<String, Object>> fetchArticleDetails(Long id) {
        List<Object> articles = articleRepository.fetchById(id);
        List<Map<String, Object>> articleList = new ArrayList<>();
        for(int i =0; i <articles.size(); i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("id",(Long)(((Object[])articles.get(i))[0]));
            data.put("createdDate",(LocalDateTime)((Object[])articles.get(i))[1]);
            data.put("doctorId",(Long)(((Object[])articles.get(i))[2]));
            data.put("title",(String)(((Object[])articles.get(i))[3]));
            data.put("coverImage",(String)(((Object[])articles.get(i))[4]));
            data.put("doctorName",(String)(((Object[])articles.get(i))[5]));
            data.put("content",(byte[])(((Object[])articles.get(i))[6]));
            data.put("profilePicture",(String)(((Object[])articles.get(i))[7]));
            articleList.add(data);
        }
        return articleList;
    }

    @Override
    public List<Map<String, Object>> fetchLatestArticles(Long id) {
        List<Object> articles = articleRepository.fetchLatestArticles(id);
        List<Map<String, Object>> articleList = new ArrayList<>();
        for(int i =0; i <articles.size(); i++) {
            Map<String, Object> data = new HashMap<>();
            data.put("id", ((Object[])articles.get(i))[0]);
            data.put("createdDate", ((Object[])articles.get(i))[1]);
            data.put("doctorId", ((Object[])articles.get(i))[2]);
            data.put("title", ((Object[])articles.get(i))[3]);
            data.put("coverImage", ((Object[])articles.get(i))[4]);
            data.put("doctorName", ((Object[])articles.get(i))[5]);
            data.put("profilePicture",(String)(((Object[])articles.get(i))[6]));
            articleList.add(data);
        }
        return articleList;
    }

    @Override
    public Optional<ArticleModel> fetchArticleModel(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
