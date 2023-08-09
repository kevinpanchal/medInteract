package com.csci5308.medinteract;

import com.jayway.jsonpath.JsonPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.Part;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TestUtil {
   public static MvcResult getResultFromPostAPI(String apiURL, String json, MockMvc mockMvc) throws Exception {
      MvcResult mvcResult =   mockMvc.perform(post(apiURL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk()).andReturn();
      return mvcResult;
    }

    public static MvcResult getResultFromGetAPI(String apiURL,MockMvc mockMvc) throws Exception {
        MvcResult mvcResult = mockMvc.perform(get(apiURL))
                .andExpect(status().isOk())
                .andReturn();
        return mvcResult;
    }
   public static boolean getErrorStatusFromMvcResult(MvcResult mvcResult) throws UnsupportedEncodingException {
        return JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.isError");

    }

    public static MvcResult getResultFromPostMultiFormAPI(String apiURL, String key, MultiValueMap<String, String> content, MockMultipartFile body, MockMvc mockMvc) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MockMultipartHttpServletRequestBuilder requestBuilder = body == null ? MockMvcRequestBuilders.multipart(apiURL):MockMvcRequestBuilders.multipart(apiURL).file(body);
        requestBuilder.headers(headers);
        requestBuilder.with(request -> {
            request.setMethod(HttpMethod.POST.name());
            request.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);
            request.setParameter(key, content.get(key).get(0));
//            request.addPart((Part) body.get("profileImage").get(0));
            return request;
        });
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
//        MvcResult mvcResult = mockMvc.perform(post(apiURL)
//                        .headers(headers)
//                        .content(body)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
        return mvcResult;
    }

    public static MvcResult getResultFromPostMultiFormAPI(String apiURL, String key, MultiValueMap<String, String> content, MockMultipartFile body, String body2, MockMvc mockMvc) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MockMultipartHttpServletRequestBuilder requestBuilder = body == null ? MockMvcRequestBuilders.multipart(apiURL):MockMvcRequestBuilders.multipart(apiURL).file(body);

        requestBuilder.headers(headers);
        requestBuilder.with(request -> {
            request.setMethod(HttpMethod.POST.name());
            request.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);
            request.setParameter(key, content.get(key).get(0));
            request.setParameter(body2, content.get(body2).get(0));
            return request;
        });
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();
        return mvcResult;
    }
}
