package com.ronmen.restendpoint.services;

import com.ronmen.restendpoint.persistence.entities.Book;
import java.util.Collection;
import org.springframework.http.ResponseEntity;

public interface IBookService {

  /**
   * Reads all books related to given userId
   *
   * @param userId
   * @return a {@link Collection} of books
   */
    Collection<Book> readBooks (String userId);

  /**
   * Add a book to the given user
   *
   * @param userId
   * @param book
   * @return a {@link ResponseEntity} with a status code of 201 (CREATED) and
   * a header (Location) that the client can consult to learn
   * how the newly created record is referencable.
   */
  Book addBook (String userId, Book book);

  /**
   * Read a book with the given userId and bookId
   *
   * @param userId
   * @param bookId
   * @return a {@Book}
   */
  Book readBook(String userId, Long bookId);

}
