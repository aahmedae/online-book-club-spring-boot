package com.github.aahmedae.onlinebookclub.service;

import com.github.aahmedae.onlinebookclub.entity.BookCollection;
import com.github.aahmedae.onlinebookclub.entity.User;
import com.github.aahmedae.onlinebookclub.repository.BookCollectionRepository;
import com.github.aahmedae.onlinebookclub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * File: UserBookService.java
 * Description: Service responsible for dealing with book-related tasks for users such as managing collections, liking books, etc...
 * Author: Asad Ahmed
 */
@Service
public class UserBookService
{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BookCollectionRepository bookCollectionRepo;

    // Creates a new collection for the given user. Returns true on success.
    public boolean createNewCollection(User user, String name, String description)
    {
        BookCollection collection = new BookCollection();

        collection.setName(name);
        collection.setDescription(description);
        collection.setBooks(new ArrayList<>());

        user.getCollections().add(collection);
        return (userRepo.save(user) != null);
    }
}
