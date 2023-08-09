package com.csci5308.medinteract.Response;

import com.csci5308.medinteract.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc
public class ResponseControllerTestIT {
    @Autowired
    MockMvc mockMvc;

    @Test
    void healthCheckTest() throws Exception {
        MvcResult mvcResult = TestUtil.getResultFromGetAPI("/server/healthCheck",mockMvc);
        boolean isError = TestUtil.getErrorStatusFromMvcResult(mvcResult);
        assertFalse(isError);
    }
}
