package com.mavcorp.jacksonobjectmapper.controllers;

import com.mavcorp.jacksonobjectmapper.domain.Book;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
public class BookController {
    @GetMapping(path = "/books")
    public Book retrieveBook() {
        Book book = Book.builder()
                .isbn("978-3-16-148410-0")
                .title("The Art of Computer Programming")
                .author("Donald Knuth")
                .yearPublished("1968")
                .build();
        log.info("Book: " + book);
        return book;
    }

    @PostMapping(path = "/books")
    public Book createBook(@RequestBody final Book book) {
        log.info("Book: " + book.toString());
        return book;
    }
}
