package com.github.aahmedae.onlinebookclub.entity;

import javax.persistence.*;
import java.util.List;

/**
 * File: BookCollection.java
 * Description: A collection of books created by a user
 * Author: Asad Ahmed
 */
@Entity
@Table(name = "book_collection")
public class BookCollection
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "description", length = 1024)
    private String description;

    @ElementCollection
    @Column(name = "books")
    private List<String> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }
}
