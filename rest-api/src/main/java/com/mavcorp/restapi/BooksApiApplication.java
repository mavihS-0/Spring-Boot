package com.mavcorp.restapi;

import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class BooksApiApplication implements CommandLineRunner {
	private final DataSource dataSource;

	public BooksApiApplication(final DataSource dataSource) {
		this.dataSource = dataSource;
    }

	public static void main(String[] args) {
		SpringApplication.run(BooksApiApplication.class, args);
	}
	@Override
	public void run(String... args) {
		log.info("Datasource: "+dataSource.toString());
		final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
		restTemplate.execute("select 1");
	}
}
