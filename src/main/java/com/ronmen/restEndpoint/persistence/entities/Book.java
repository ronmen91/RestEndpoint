package com.ronmen.restEndpoint.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    private User user;

    private String uri;

    private String description;

    private Book() {}

    public Book(User user, String uri, String description) {
        this.user = user;
        this.uri = uri;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getUri() {
        return uri;
    }

    public User getUser() {
        return user;
    }
}
