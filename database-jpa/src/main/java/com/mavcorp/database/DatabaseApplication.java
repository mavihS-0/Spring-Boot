package com.mavcorp.database;

import com.mavcorp.database.domain.Author;
import com.mavcorp.database.repositories.AuthorRepository;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class DatabaseApplication implements CommandLineRunner {
	private final DataSource dataSource;

	private final AuthorRepository authorRepository;

	public DatabaseApplication(final DataSource dataSource, AuthorRepository authorRepository) {
		this.dataSource = dataSource;
        this.authorRepository = authorRepository;
    }

	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
	}
	@Override
	public void run(String... args) {
		log.info("Datasource: "+dataSource.toString());
		final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
		restTemplate.execute("select 1");
		Author author = Author.builder().id(1L).name("SHIVAM").age(20).build();
		authorRepository.save(author);
	}
}
