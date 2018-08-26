package com.ronmen.restEndpoint.persistence.repository;

import com.ronmen.restEndpoint.persistence.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByUserUsername(String username);
}
