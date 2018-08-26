package com.ronmen.restendpoint.controllers;

import com.ronmen.restendpoint.persistence.entities.Book;
import com.ronmen.restendpoint.services.impl.BookService;
import java.net.URI;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/books/{userId}")
public class BookRestController {
  private BookService bookService;

  @Autowired
  public BookRestController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  Collection<Book> readBooks(@PathVariable String userId) {
    return  this.bookService.readBooks(userId);
  }

  @PostMapping
  ResponseEntity<?> add(@PathVariable String userId, @RequestBody Book book) {
    return bookService.addBook(userId, book)
        .map(savedBook -> {
          URI location = ServletUriComponentsBuilder
          .fromCurrentRequest()
          .path("/{id}")
          .buildAndExpand(savedBook.getId())
          .toUri();

          return ResponseEntity.created(location).build();
        })
      .orElse(ResponseEntity.noContent().build());
  }

  @GetMapping("/{bookId}")
  Book readBook(@PathVariable String userId, @PathVariable Long bookId) {
    return this.bookService.readBook(userId, bookId);
  }
}
