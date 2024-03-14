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

    public static Book createTestBookA(final Author author) {
        return Book.builder().isbn("831-231-31-3").title("Book1").author(author).build();
    }
    public static Book createTestBookB(final Author author) {
        return Book.builder().isbn("999-651-31-3").title("Hurry Puttar").author(author).build();
    }
    public static Book createTestBookC(final Author author) {
        return Book.builder().isbn("193-131-31-3").title("What is a book").author(author).build();
    }
}
