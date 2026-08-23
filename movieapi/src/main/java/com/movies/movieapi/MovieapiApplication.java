package com.movies.movieapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.movies")
@EntityScan("com.movies.entities")
@EnableJpaRepositories("com.movies.Repositories")
public class MovieapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieapiApplication.class, args);
	}

}
