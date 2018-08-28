package com.ronmen.restendpoint.services.impl;

import com.ronmen.restendpoint.persistence.repository.UserRepository;
import com.ronmen.restendpoint.services.IUserService;
import com.ronmen.restendpoint.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
  private final UserRepository userRepository;

  @Autowired
  public UserService (UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void validateUser(String userName) {
    this.userRepository
        .findByUsername(userName)
        .orElseThrow(() -> new UserNotFoundException(userName));
  }
}
