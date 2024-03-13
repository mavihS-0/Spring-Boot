package com.mavcorp.database.dao;

import com.mavcorp.database.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void create(Book book);

    Optional<Book> findOne(String l);

    List<Book> findMany();

    void update(String isbn, Book book);

    void delete(String isbn);
}

