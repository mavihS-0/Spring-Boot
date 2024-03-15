package com.mavcorp.database.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mavcorp.database.TestDataUtil;
import com.mavcorp.database.domain.entities.BookEntity;
import com.mavcorp.database.services.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc,BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookReturnsHttp201Created() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        String bookJson = objectMapper.writeValueAsString(bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+ bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookReturnsSavedAuthor() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        String bookJson = objectMapper.writeValueAsString(bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+ bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookEntity.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(bookEntity.getAuthorEntity())
        );
    }

    @Test
    public void testThatListBooksReturnsHttp200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBooksReturnsSavedBooks() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(MockMvcRequestBuilders.get("/books")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(bookEntity.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].author").value(bookEntity.getAuthorEntity())
        );
    }

    @Test
    public void testThatGetBookReturnsHttp200Ok() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/"+ bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBookReturnsSavedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/"+ bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookEntity.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(bookEntity.getAuthorEntity())
        );
    }

    @Test
    public void testThatGetBookReturnsHttp404NotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/123")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatUpdateBookReturnsHttp200Ok() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
        String bookJson = objectMapper.writeValueAsString(bookEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+ bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
        BookEntity bookEntityB = TestDataUtil.createTestBookB(null);
        String bookJson = objectMapper.writeValueAsString(bookEntityB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/"+ bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookEntityB.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(bookEntityB.getAuthorEntity())
        );
    }

    @Test
    public void testThatPartialUpdateBookReturnsHttp200Ok() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
        BookEntity bookEntityB = TestDataUtil.createTestBookB(null);
        String bookJson = objectMapper.writeValueAsString(bookEntityB);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/"+ bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
        BookEntity bookEntityB = TestDataUtil.createTestBookB(null);
        String bookJson = objectMapper.writeValueAsString(bookEntityB);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/"+ bookEntity.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookEntity.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookEntityB.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").value(bookEntityB.getAuthorEntity())
        );
    }
}
