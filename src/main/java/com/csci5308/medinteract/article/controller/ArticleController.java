package com.csci5308.medinteract.article.controller;

import com.csci5308.medinteract.article.model.ArticleModel;
import com.csci5308.medinteract.article.service.ArticleService;
import com.csci5308.medinteract.doctor.model.DoctorModel;
import com.csci5308.medinteract.doctor.service.DoctorService;

import com.csci5308.medinteract.LocalDateTime.LocalDateTimeDeserializer;
import com.csci5308.medinteract.Response.Response;
import com.google.gson.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.csci5308.medinteract.FileUpload.FileUploader.saveFile;

@RestController
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleServiceImpl;

    private final DoctorService doctorServiceImpl;

    public static final int COUNT = 10;
    public static final int SPLIT_COUNT = 2;

    @Autowired
    public ArticleController(ArticleService articleServiceImpl, DoctorService doctorServiceImpl) {
        this.articleServiceImpl = articleServiceImpl;
        this.doctorServiceImpl = doctorServiceImpl;
    }

    @PostMapping(path = "/addArticle", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity addArticle(@RequestParam MultiValueMap<String, String> formData, @RequestParam(value = "content") String content, @RequestParam(value = "coverImage") MultipartFile multipartFile) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).create();
        ArticleModel articleModel = gson.fromJson(formData.getFirst("objectData"), ArticleModel.class);
        Optional<DoctorModel> doctorModel = doctorServiceImpl.getDoctorById(articleModel.getDoctorId());
        if (doctorModel.isEmpty()) {
            Response res = new Response("", true, "An unknown error occurred!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        String fileName = RandomStringUtils.randomAlphanumeric(COUNT) + ".jpeg";
//        StringUtils.cleanPath(multipartFile.getOriginalFilename())
        String uploadDir = "user-photos/blog/";
        articleModel.setCoverImage(uploadDir + fileName);
        articleModel.setContent(content.getBytes());
        articleModel.setActive(true);
        try {
            saveFile(uploadDir, fileName, multipartFile);
        } catch (IOException e) {
            Response res = new Response("", true, "Error while saving article");
            return new ResponseEntity(res, HttpStatus.OK);
        }
        articleModel = articleServiceImpl.saveArticle(articleModel);
        Response res = new Response(articleModel, false, "Article added successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @PostMapping(path = "/updateArticle", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateArticle(@RequestParam MultiValueMap<String, String> formData, @RequestParam(value = "content") String content, @RequestParam(value = "coverImage", required = false) MultipartFile multipartFile) {
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer()).create();
        ArticleModel articleModel = gson.fromJson(formData.getFirst("objectData"), ArticleModel.class);
        Optional<DoctorModel> doctorModel = doctorServiceImpl.getDoctorById(articleModel.getDoctorId());
        if (doctorModel.isEmpty()) {
            Response res = new Response("", true, "An unknown error occurred!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        Optional<ArticleModel> fetchArticleModel = articleServiceImpl.fetchArticleModel(articleModel.getId());
        if(fetchArticleModel.isEmpty()) {
            Response res = new Response(null, true, "Unable to update article!");
            return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
        }
        ArticleModel oldArticleModel = fetchArticleModel.get();
        articleModel.setContent(content.getBytes());
        articleModel.setActive(true);
        if (multipartFile != null && !multipartFile.isEmpty()) {
            String fileName;
            if (oldArticleModel.getCoverImage()!= null) {
                fileName = oldArticleModel.getCoverImage().split("/")[SPLIT_COUNT];
            } else {
                fileName = RandomStringUtils.randomAlphanumeric(COUNT) + ".jpeg";
            }
            String uploadDir = "user-photos/blog/";
            try {
                saveFile(uploadDir, fileName, multipartFile);
            } catch (IOException e) {
                Response res = new Response("", true, "Error while updating article!");
                return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
            }
            articleModel.setCoverImage(uploadDir + fileName);
        } else {
            articleModel.setCoverImage(oldArticleModel.getCoverImage());
        }
        articleModel = articleServiceImpl.saveArticle(articleModel);
        Response res = new Response(articleModel, false, "Article added successfully!");
        return new ResponseEntity<>(res.getResponse(), HttpStatus.OK);
    }

    @GetMapping("/fetchAllArticles")
    public ResponseEntity fetchAllArticles() {
        List<Map<String, Object>> articleModelList = articleServiceImpl.fetchAllArticles();
        Response res = new Response(articleModelList, false, "Articles fetched successfully");
        return new ResponseEntity(res.getResponse(), HttpStatus.OK);
    }

    @GetMapping("/fetchDoctorArticle/{doctorId}")
    public ResponseEntity fetchAllDoctorArticles(@PathVariable("doctorId") Long id) {
        List<Map<String, Object>> articleModelList = articleServiceImpl.fetchAllDoctorArticles(id);
        Response res = new Response(articleModelList, false, "Articles fetched successfully");
        return new ResponseEntity(res.getResponse(), HttpStatus.OK);
    }

    @GetMapping("/getArticle/{articleId}")
    public ResponseEntity fetchArticle(@PathVariable("articleId") Long id) {
        List<Map<String, Object>> articleModel = articleServiceImpl.fetchArticleDetails(id);
        if (articleModel.isEmpty()) {
            Response res = new Response("", true, "Error while fetching article!");
            return new ResponseEntity(res.getResponse(), HttpStatus.OK);
        }
        Response res = new Response(articleModel.get(0), false, "Article fetched successfully");
        return new ResponseEntity(res.getResponse(), HttpStatus.OK);
    }

    @GetMapping("/getArticleWithSuggestion/{articleId}")
    public ResponseEntity fetchArticleWithSuggestion(@PathVariable("articleId") Long id) {
        List<Map<String, Object>> articleModel = articleServiceImpl.fetchArticleDetails(id);
        if (articleModel.isEmpty()) {
            Response res = new Response("", true, "Error while fetching article!");
            return new ResponseEntity(res.getResponse(), HttpStatus.OK);
        }
        List<Map<String, Object>> suggestionList = articleServiceImpl.fetchLatestArticles(id);
        List<Object> objectList = new ArrayList<>();
        objectList.add(articleModel.get(0));
        objectList.add(suggestionList);
        Response res = new Response(objectList, false, "Article fetched successfully");
        return new ResponseEntity(res.getResponse(), HttpStatus.OK);
    }

    @GetMapping("/deleteArticle/{articleId}")
    public ResponseEntity deleteArticle(@PathVariable("articleId") Long id) {
        Optional<ArticleModel> articleModel = articleServiceImpl.fetchArticleModel(id);
        if (articleModel.isEmpty()) {
            Response res = new Response("", true, "Error while deleting article!");
            return new ResponseEntity(res.getResponse(), HttpStatus.OK);
        }
        articleModel.get().setActive(false);
        articleServiceImpl.deleteArticle(id);

        Response res = new Response(articleModel.get(), false, "Article deleted successfully");
        return new ResponseEntity(res.getResponse(), HttpStatus.OK);
    }
}
