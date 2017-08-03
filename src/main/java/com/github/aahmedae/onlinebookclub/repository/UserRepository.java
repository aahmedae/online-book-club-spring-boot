package com.github.aahmedae.onlinebookclub.repository;

import com.github.aahmedae.onlinebookclub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * File: FILE_NAME
 * Description: DESCRIPTION
 * Author: asad
 */
public interface UserRepository extends JpaRepository<User, Long>
{
    // Find a user by the username
    User findByUsername(String username);
}
