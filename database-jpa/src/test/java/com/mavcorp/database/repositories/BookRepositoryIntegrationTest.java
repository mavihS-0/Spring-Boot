package com.mavcorp.database.repositories;

import com.mavcorp.database.TestDataUtil;
import com.mavcorp.database.domain.Author;
import com.mavcorp.database.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTest {

    private final BookRepository underTest;
    @Autowired
    public BookRepositoryIntegrationTest(BookRepository underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA(author);
        underTest.save(book);
        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }
//
    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Book bookA = TestDataUtil.createTestBookA(authorA);
        Book bookB = TestDataUtil.createTestBookB(authorB);
        Book bookC = TestDataUtil.createTestBookC(authorA);
        underTest.save(bookA);
        underTest.save(bookB);
        underTest.save(bookC);

        assertThat(underTest.findAll()).hasSize(3).containsExactly(bookA, bookB, bookC);
    }
//
    @Test
    public void testThatBookCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA(author);
        underTest.save(book);
        book.setTitle("Updated Title");
        underTest.save(book);
        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA(author);
        underTest.save(book);
        underTest.deleteById(book.getIsbn());
        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isEmpty();
    }

}
