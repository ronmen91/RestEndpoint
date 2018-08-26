package com.ronmen.restEndpoint.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Book> books = new ArrayList<>();

    private User() {} //for JPA

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Book> getBooks() {
        return books;
    }
}
