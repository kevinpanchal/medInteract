package com.csci5308.medinteract.FileUpload;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import static org.junit.jupiter.api.Assertions.assertFalse;

class FileUploadConfigTest {
    @Test
    void testAddResourceHandlers() {

        // Arrange
        FileUploadConfig fileUploadConfig = new FileUploadConfig();
        AnnotationConfigReactiveWebApplicationContext applicationContext = new AnnotationConfigReactiveWebApplicationContext();
        ResourceHandlerRegistry registry = new ResourceHandlerRegistry(applicationContext, new MockServletContext());

        // Act
        fileUploadConfig.addResourceHandlers(registry);

        // Assert
        assertFalse(registry.hasMappingForPattern("Path Pattern"));
    }
}

