package com.ronmen.restendpoint.controllers;

import com.ronmen.restendpoint.persistence.entities.Book;
import com.ronmen.restendpoint.persistence.repository.UserRepository;
import com.ronmen.restendpoint.services.impl.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;


@RestController
@RequestMapping("/books")
public class BookRestController {
  private BookService bookService;
  private UserRepository userRepository;

  @Autowired
  public BookRestController(BookService bookService, UserRepository userRepository) {
    this.bookService = bookService;
    this.userRepository = userRepository;
  }

  @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
  ResourceSupport root() {
    ResourceSupport root = new ResourceSupport();

    root.add(this.userRepository.findAll().stream()
            .map(account -> linkTo(methodOn(BookRestController.class)
                    .readBooks(account.getUsername()))
                    .withRel(account.getUsername()))
            .collect(Collectors.toList()));

    return root;
  }

  @GetMapping(value = "/{userId}", produces = MediaTypes.HAL_JSON_VALUE)
  Resources<Resource<Book>> readBooks(@PathVariable String userId) {
    return  new Resources<>(this.bookService.readBooks(userId).stream()
            .map(book -> toResource(book, userId))
            .collect(Collectors.toList()));
  }

  @PostMapping("/{userId}")
  ResponseEntity<?> add(@PathVariable String userId, @RequestBody Book book) {
    return bookService.addBook(userId, book)
        .map(savedBook -> ResponseEntity.created(
                URI.create(
                        toResource(savedBook, userId).getLink(Link.REL_SELF).getHref()))
                .build())
      .orElse(ResponseEntity.noContent().build());
  }

  @GetMapping(value = "/{userId}/{bookId}", produces = MediaTypes.HAL_JSON_VALUE)
  Resource<Book> readBook(@PathVariable String userId, @PathVariable Long bookId) {
    return this.bookService.readBook(userId, bookId)
            .map(book -> toResource(book, userId))
            .get();
  }

  private static Resource<Book> toResource (Book book, String userId) {
    return new Resource<>(book,
            new Link(book.getUri(), "book-uri"),
            linkTo(methodOn(BookRestController.class).readBooks(userId)).withRel("books"),
            linkTo(methodOn(BookRestController.class).readBook(userId, book.getId())).withSelfRel());
  }
}
