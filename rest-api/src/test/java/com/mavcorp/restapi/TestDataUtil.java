package com.mavcorp.restapi;

import com.mavcorp.restapi.domain.entities.AuthorEntity;
import com.mavcorp.restapi.domain.entities.BookEntity;

public class TestDataUtil {
    private TestDataUtil() {
    }

    public static AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder().id(1L).name("SHIVAM").age(20).build();
    }
    public static AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder().id(2L).name("Mavihs").age(50).build();
    }
    public static AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder().id(3L).name("ALICE").age(15).build();
    }

    public static BookEntity createTestBookA(final AuthorEntity authorEntity) {
        return BookEntity.builder().isbn("831-231-31-3").title("Book1").authorEntity(authorEntity).build();
    }
    public static BookEntity createTestBookB(final AuthorEntity authorEntity) {
        return BookEntity.builder().isbn("999-651-31-3").title("Hurry Puttar").authorEntity(authorEntity).build();
    }
    public static BookEntity createTestBookC(final AuthorEntity authorEntity) {
        return BookEntity.builder().isbn("193-131-31-3").title("What is a book").authorEntity(authorEntity).build();
    }
}
