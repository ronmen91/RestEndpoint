package com.ronmen.restendpoint.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException{
  public BookNotFoundException (Long bookId) {
    super("Book is not found with bookid: " + bookId);
  }
}
