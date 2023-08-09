package com.csci5308.medinteract.JWT;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {JWT.class})
@ExtendWith(SpringExtension.class)

class JWTTest {
    @Autowired
    private JWT jWT;

    private static final String SECRET_KEY = "mySecretKey";

    @Test
    void testGenerateTokenSize() {
        assertEquals(2, jWT.generateToken("MeMoRam", "Type", "Obj").size());
    }
}

