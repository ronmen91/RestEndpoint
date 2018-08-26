package com.ronmen.restendpoint.services.impl;

import com.ronmen.restendpoint.persistence.entities.Book;
import com.ronmen.restendpoint.persistence.entities.User;
import com.ronmen.restendpoint.persistence.repository.BookRepository;
import com.ronmen.restendpoint.persistence.repository.UserRepository;
import com.ronmen.restendpoint.services.IBookService;
import com.ronmen.restendpoint.services.exceptions.BookNotFoundException;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService implements IBookService {
  private UserService userService;
  private BookRepository bookRepository;
  private UserRepository userRepository;

  @Autowired
  public BookService(UserService userService, BookRepository bookRepository,
      UserRepository userRepository) {
    this.userService = userService;
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
  }

  @Override
  public Collection<Book> readBooks(String userId) {
    this.userService.validateUser(userId);
    return this.bookRepository.findByUserUsername(userId);
  }

  @Override
  public Optional<Book> addBook(String userId, Book book) {
    this.userService.validateUser(userId);
    Optional<User> user = this.userRepository.findByUsername(userId);

    return Optional.of(this.bookRepository
        .save(new Book (user.get(), book.getUri(), book.getDescription())));
  }

  @Override
  public Book readBook(String userId, Long bookId) {
    this.userService.validateUser(userId);
    return this.bookRepository
        .findById(bookId)
        .orElseThrow(() -> new BookNotFoundException(bookId));
  }
}
