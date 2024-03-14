package com.mavcorp.jacksonobjectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mavcorp.jacksonobjectmapper.domain.Book;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class JacksonTests {

    @Test
    public void testThatObjectMapperCanCreateJsonFromJavaObject() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final Book book = Book.builder()
                .isbn("978-3-16-148410-0")
                .title("The Art of Computer Programming")
                .author("Donald Knuth")
                .yearPublished("1968")
                .build();
        String result = objectMapper.writeValueAsString(book);
        assertThat(result).isEqualTo("{\"isbn\":\"978-3-16-148410-0\",\"title\":\"The Art of Computer Programming\",\"author\":\"Donald Knuth\",\"year\":\"1968\"}");
    }

    @Test
    public void testThatObjectMapperCanCreateJavaObjectFromJson() throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        final String json = "{\"name\":\"mav\",\"isbn\":\"978-3-16-148410-0\",\"title\":\"The Art of Computer Programming\",\"author\":\"Donald Knuth\",\"year\":\"1968\"}";
        Book result = objectMapper.readValue(json, Book.class);
        assertThat(result).isEqualTo(Book.builder()
                .isbn("978-3-16-148410-0")
                .title("The Art of Computer Programming")
                .author("Donald Knuth")
                .yearPublished("1968")
                .build());
    }
}
