package com.csci5308.medinteract.JWT;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.impl.DefaultClaims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {JWTController.class})
@ExtendWith(SpringExtension.class)
class JWTControllerTest {
    @MockBean
    private JWT jWT;

    @Autowired
    private JWTController jWTController;

    @Test
    void testValidateJWTTokenTrue() throws Exception {

        // Arrange
        when(jWT.validateToken(Mockito.<String>any())).thenReturn(true);
        when(jWT.extractClaims(Mockito.<String>any())).thenReturn(new DefaultClaims());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/jwt/validateJWTToken")
                .contentType(MediaType.APPLICATION_JSON);
        // Act
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content((new ObjectMapper()).writeValueAsString("memo"));
        // Assert
        MockMvcBuilders.standaloneSetup(jWTController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"msg\":\"Token is Valid\",\"isError\":false,\"data\":{}}"));
    }
    @Test
    void testValidateJWTTokenFalse() throws Exception {

        // Arrange
        when(jWT.validateToken(Mockito.<String>any())).thenReturn(false);
        when(jWT.extractClaims(Mockito.<String>any())).thenReturn(new DefaultClaims());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/jwt/validateJWTToken")
                .contentType(MediaType.APPLICATION_JSON);
        //Act
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content((new ObjectMapper()).writeValueAsString("memo"));
        // Assert
        MockMvcBuilders.standaloneSetup(jWTController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"msg\":\"Token is InValid\",\"isError\":true,\"data\":{}}"));
    }
}

