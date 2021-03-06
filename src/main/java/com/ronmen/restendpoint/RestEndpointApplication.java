package com.ronmen.restendpoint;

import com.ronmen.restendpoint.persistence.entities.Book;
import com.ronmen.restendpoint.persistence.entities.User;
import com.ronmen.restendpoint.persistence.repository.BookRepository;
import com.ronmen.restendpoint.persistence.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class RestEndpointApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestEndpointApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository, BookRepository bookRepository) {
		return args ->
				Arrays.asList("rnemeth","ebotos","bkocsis","edroberts","takovacs","glaszlo")
						.forEach(username -> {
							User user = userRepository.save(new User(username, "password"));
							bookRepository.save(new Book(user, "Book 1"));
							bookRepository.save(new Book(user, "Book 2"));
							bookRepository.save(new Book(user, "Book 3"));
						});
	}
}
