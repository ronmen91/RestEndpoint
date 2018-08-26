package com.ronmen.restEndpoint.persistence.repository;

import com.ronmen.restEndpoint.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
