package com.mavcorp.database.services;

import com.mavcorp.database.domain.entities.AuthorEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AuthorService {
    AuthorEntity createAuthor(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();
}
