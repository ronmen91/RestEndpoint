package com.ronmen.restendpoint.persistence.repository;

import com.ronmen.restendpoint.persistence.entities.Book;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Collection<Book> findByUserUsername(String username);
}
