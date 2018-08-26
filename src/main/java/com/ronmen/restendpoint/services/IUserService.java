package com.ronmen.restendpoint.services;

import com.ronmen.restendpoint.persistence.entities.User;
import org.springframework.http.HttpStatus;

public interface IUserService {
  /**
   * Validate whether a {@link User} exists with the given userId.
   * The implemented solution has to handle situation where user is not found.
   * It's highly recommended to sign negative result a specific {@link RuntimeException}
   * which also sets the HTTP status code as well (to e.g.: {@link HttpStatus#NOT_FOUND})
   *
   * @param userId
   */
  void validateUser (String userId);
}
