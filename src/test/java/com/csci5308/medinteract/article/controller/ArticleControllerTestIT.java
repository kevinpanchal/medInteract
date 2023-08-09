package com.csci5308.medinteract.article.controller;

import com.csci5308.medinteract.TestUtil;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;

@WebAppConfiguration
@ContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class ArticleControllerTestIT {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addArticle() throws Exception {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        JSONObject obj = new JSONObject();
        obj.put("title", "title");
        obj.put("doctorId", "13");
        String json = obj.toString();
        formData.add("objectData", json);
        formData.add("content", "content");
        String apiURL = "/article/addArticle";
        File file = new File("./src/test/resources/JLmd2P5uty.jpeg");
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("coverImage", file.getName(), "image/jpeg", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = TestUtil.getResultFromPostMultiFormAPI(apiURL, "objectData", formData, multipartFile, "content", mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }

    @Test
    void addInvalidDoctorArticle() throws Exception {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        JSONObject obj = new JSONObject();
        obj.put("title", "title");
        obj.put("doctorId", "-1");
        String json = obj.toString();
        formData.add("objectData", json);
        formData.add("content", "content");
        String apiURL = "/article/addArticle";
        File file = new File("./src/test/resources/JLmd2P5uty.jpeg");
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("coverImage", file.getName(), "image/jpeg", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = TestUtil.getResultFromPostMultiFormAPI(apiURL, "objectData", formData, multipartFile, "content", mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertTrue(isError);
    }

    @Test
    void updateArticle() throws Exception {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        JSONObject obj = new JSONObject();
        obj.put("title", "title");
        obj.put("doctorId", "13");
        obj.put("id", "202");
        String json = obj.toString();
        formData.add("objectData", json);
        formData.add("content", "content");
        String apiURL = "/article/updateArticle";
        File file = new File("./src/test/resources/JLmd2P5uty.jpeg");
        FileInputStream fileInputStream = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("coverImage", file.getName(), "image/jpeg", fileInputStream);
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = TestUtil.getResultFromPostMultiFormAPI(apiURL, "objectData", formData, multipartFile, "content", mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }

    @Test
    void updateArticleWithoutImage() throws Exception {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        JSONObject obj = new JSONObject();
        obj.put("title", "title");
        obj.put("doctorId", "13");
        obj.put("id", "202");
        String json = obj.toString();
        formData.add("objectData", json);
        formData.add("content", "content");
        String apiURL = "/article/updateArticle";
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = TestUtil.getResultFromPostMultiFormAPI(apiURL, "objectData", formData, null, "content", mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }

    @Test
    void updateArticleWitInvalidID() throws Exception {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        JSONObject obj = new JSONObject();
        obj.put("title", "title");
        obj.put("doctorId", "13");
        obj.put("id", "-1");
        String json = obj.toString();
        formData.add("objectData", json);
        formData.add("content", "content");
        String apiURL = "/article/updateArticle";
        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult mvcResult = TestUtil.getResultFromPostMultiFormAPI(apiURL, "objectData", formData, null, "content", mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertTrue(isError);
    }

    @Test
    void fetchAllArticles() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/article/fetchAllArticles",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertEquals("Articles fetched successfully", msg);
    }

    @Test
    void fetchAllDoctorArticles() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/article/fetchDoctorArticle/1",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertEquals("Articles fetched successfully", msg);
    }

    @Test
    void fetchArticle() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/article/getArticle/1177",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertEquals("Article fetched successfully", msg);
    }

    @Test
    void fetchInvalidArticle() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/article/getArticle/1",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertEquals("Error while fetching article!", msg);
    }

    @Test
    void fetchArticleWithSuggestion() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/article/getArticleWithSuggestion/1177",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertEquals("Article fetched successfully", msg);
    }

    @Test
    void fetchArticleWithInvalidSuggestion() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/article/getArticleWithSuggestion/1",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertEquals("Error while fetching article!", msg);
    }

    @Test
    void deleteInvalidArticle() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/article/deleteArticle/-1",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertEquals("Error while deleting article!", msg);
    }

    @Test
    void deleteArticle() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/article/deleteArticle/1178",mockMvc);
        String msg = JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.msg");
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertEquals("Article deleted successfully", msg);
    }
}
