package com.mavcorp.database.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mavcorp.database.TestDataUtil;
import com.mavcorp.database.domain.entities.AuthorEntity;
import com.mavcorp.database.services.AuthorService;
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
public class AuthorControllerIntegrationTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private AuthorService authorService;

    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc,AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
        this.authorService = authorService;
    }

    @Test
    public void testThatCreateAuthorReturnsHttp201Created() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorEntity.setId(null);

        String authorJson = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorReturnsSavedAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorEntity.setId(null);

        String authorJson = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorEntity.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorEntity.getAge())
        );
    }

    @Test
    public void testThatListAuthorsReturnsHttp200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
        AuthorEntity authorEntityA = TestDataUtil.createTestAuthorA();
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        AuthorEntity authorEntityC = TestDataUtil.createTestAuthorC();
        authorService.save(authorEntityA);
        authorService.save(authorEntityB);
        authorService.save(authorEntityC);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$").isArray()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value(authorEntityA.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(authorEntityA.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].name").value(authorEntityB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[1].age").value(authorEntityB.getAge())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[2].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[2].name").value(authorEntityC.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[2].age").value(authorEntityC.getAge())
        );
    }

    @Test
    public void testThatGetAuthorReturnsHttp200Ok() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/author?id="+authorEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAuthorReturnsAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/author?id="+authorEntity.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorEntity.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorEntity.getAge())
        );
    }

    @Test
    public void testThatGetAuthorReturnsHttp404NotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/author?id=2")
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttp200Ok() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);
        authorEntity.setName("Updated Name");
        String authorJson = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+authorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsUpdatedAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        String authorBJson = objectMapper.writeValueAsString(authorEntityB);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/"+authorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorBJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorEntityB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorEntityB.getAge())
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttp404NotFound() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        String authorJson = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateAuthorReturnsHttp200Ok() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);
        authorEntity.setName("Updated Name");
        String authorJson = objectMapper.writeValueAsString(authorEntity);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/"+authorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateAuthorReturnsUpdatedAuthor() throws Exception {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        authorService.save(authorEntity);
        AuthorEntity authorEntityB = TestDataUtil.createTestAuthorB();
        String authorBJson = objectMapper.writeValueAsString(authorEntityB);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/"+authorEntity.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorBJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorEntityB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorEntityB.getAge())
        );
    }

}
