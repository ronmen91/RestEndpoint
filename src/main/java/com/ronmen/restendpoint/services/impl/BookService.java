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
  public Collection<Book> readBooks(String userName) {
    this.userService.validateUser(userName);
    return this.bookRepository.findByUserUsername(userName);
  }

  @Override
  public Optional<Book> addBook(String userName, Book book) {
    this.userService.validateUser(userName);
    Optional<User> user = this.userRepository.findByUsername(userName);

    return Optional.of(this.bookRepository
        .save(new Book (user.get(), book.getDescription())));
  }

  @Override
  public Optional<Book> readBook(String userName, Long bookId) {
    this.userService.validateUser(userName);
    return Optional.of(this.bookRepository
            .findByIdAndUserUsername(bookId, userName)
            .orElseThrow(() -> new BookNotFoundException(bookId)));
  }
}
