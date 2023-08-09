package com.csci5308.medinteract.FileUpload;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileUploaderTest {

    @Test
    void testSaveFile(@TempDir Path tempDir) throws IOException {

        // Arrange
        String uploadDir = tempDir.toString();
        String fileName = "testFile.txt";
        String fileContent = "This is a test file.";
        byte[] fileBytes = fileContent.getBytes();
        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("file", fileName, "text/plain", fileBytes);

        // Act
        FileUploader.saveFile(uploadDir, fileName, mockMultipartFile);

        // Assert
        Path savedFilePath = tempDir.resolve(fileName);
        assertArrayEquals(fileBytes, Files.readAllBytes(savedFilePath));
    }

}

