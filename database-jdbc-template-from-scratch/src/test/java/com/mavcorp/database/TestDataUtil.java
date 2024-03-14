package com.mavcorp.database;

import com.mavcorp.database.domain.Author;
import com.mavcorp.database.domain.Book;

public class TestDataUtil {
    private TestDataUtil() {
    }

    public static Author createTestAuthorA() {
        return Author.builder().id(1L).name("SHIVAM").age(20).build();
    }
    public static Author createTestAuthorB() {
        return Author.builder().id(2L).name("Mavihs").age(50).build();
    }
    public static Author createTestAuthorC() {
        return Author.builder().id(3L).name("ALICE").age(15).build();
    }

    public static Book createTestBookA() {
        return Book.builder().isbn("831-231-31-3").title("Book1").authorId(1L).build();
    }
    public static Book createTestBookB() {
        return Book.builder().isbn("999-651-31-3").title("Hurry Puttar").authorId(2L).build();
    }
    public static Book createTestBookC() {
        return Book.builder().isbn("193-131-31-3").title("What is a book").authorId(1L).build();
    }
}
