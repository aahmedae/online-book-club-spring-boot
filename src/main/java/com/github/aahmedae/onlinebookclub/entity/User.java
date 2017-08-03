package com.github.aahmedae.onlinebookclub.entity;

import javax.persistence.*;
import java.util.List;

/**
 * File: User.java
 * Description: Maps to a user account
 * Author: Asad Ahmed
 */
@Entity
@Table(name = "user")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToMany(targetEntity = User.class)
    private List<User> friends;

    @OneToMany(targetEntity = BookCollection.class)
    private List<BookCollection> collections;

    @OneToMany(targetEntity = BookCollection.class)
    private List<BookCollection> likedCollections;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<BookCollection> getCollections() {
        return collections;
    }

    public void setCollections(List<BookCollection> collections) {
        this.collections = collections;
    }

    public List<BookCollection> getLikedCollections() {
        return likedCollections;
    }

    public void setLikedCollections(List<BookCollection> likedCollections) {
        this.likedCollections = likedCollections;
    }
}
