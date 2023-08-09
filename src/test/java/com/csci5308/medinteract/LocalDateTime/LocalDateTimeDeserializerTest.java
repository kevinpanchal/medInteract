package com.csci5308.medinteract.LocalDateTime;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LocalDateTimeDeserializerTest {
    private LocalDateTimeDeserializer deserializer;

    @BeforeEach
    void setUp() {
        deserializer = new LocalDateTimeDeserializer();
    }

    @Test
    void deserializeValidJsonElement() {

        // Arrange
        JsonElement json = new JsonPrimitive("2022-04-01 12:34:56");
        Type typeOfT = LocalDateTime.class;
        JsonDeserializationContext context = mock(JsonDeserializationContext.class);

        // Act
        LocalDateTime result = deserializer.deserialize(json, typeOfT, context);

        // Assert
        LocalDateTime expected = LocalDateTime.of(2022, 4, 1, 12, 34, 56);
        assertEquals(expected, result);
    }

    @Test
    void deserializeInvalidJsonElement() {

        // Arrange
        JsonElement json = new JsonPrimitive("Test String");
        Type typeOfT = LocalDateTime.class;
        JsonDeserializationContext context = mock(JsonDeserializationContext.class);

        // Act and Assert
        assertThrows(DateTimeParseException.class, () -> deserializer.deserialize(json, typeOfT, context));
    }


    private JsonDeserializationContext mock(Class<JsonDeserializationContext> classToMock) {
        return Mockito.mock(classToMock);
    }
}

