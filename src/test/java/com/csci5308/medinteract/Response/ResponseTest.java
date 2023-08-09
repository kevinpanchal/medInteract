
package com.csci5308.medinteract.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Map;

import org.junit.jupiter.api.Test;

class ResponseTest {

    @Test
    void testConstructor() {
        assertEquals(4, (new Response(
                "myBody",
                true,
                "myMessage",
                3)).getResponse().size());
    }
    @Test
    void testGetResponse() {
        Response response = new Response("myBody", true, "myMassage");
        Map<String, Object> actualResponse = response.getResponse();
        assertEquals(3, actualResponse.size());
    }
}
