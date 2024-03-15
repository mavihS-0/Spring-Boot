package com.mavcorp.database.controllers;

import com.mavcorp.database.domain.dto.AuthorDto;
import com.mavcorp.database.domain.entities.AuthorEntity;
import com.mavcorp.database.mappers.Mapper;
import com.mavcorp.database.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    private final Mapper<AuthorEntity,AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity,AuthorDto> authorMapper){
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author){
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity =  authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/authors")
    public ResponseEntity<List<AuthorDto>> listAuthors(){
        List<AuthorEntity> authors = authorService.findAll();
        return new ResponseEntity<>(authors.stream().map(authorMapper::mapTo).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(path = "/author")
    public ResponseEntity<AuthorDto> getAuthor(@RequestParam("id") Long id){
        Optional<AuthorEntity> foundAuthor = authorService.findOne(id);
        return foundAuthor.map(authorEntity -> new ResponseEntity<>(authorMapper.mapTo(authorEntity), HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> fullUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto author){
        if(!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        author.setId(id);
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity updatedAuthorEntity = authorService.save(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthorEntity), HttpStatus.OK);
    }

    @PatchMapping(path = "/authors/{id}")
    public ResponseEntity<AuthorDto> partialUpdateAuthor(@PathVariable("id") Long id, @RequestBody AuthorDto authorDto){
        if(!authorService.isExists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity updatedAuthor = authorService.partialUpdate(id,authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(updatedAuthor), HttpStatus.OK);
    }
}

