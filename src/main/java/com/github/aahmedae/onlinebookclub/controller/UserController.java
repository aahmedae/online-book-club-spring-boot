package com.github.aahmedae.onlinebookclub.controller;

import com.github.aahmedae.onlinebookclub.entity.User;
import com.github.aahmedae.onlinebookclub.repository.UserRepository;
import com.github.aahmedae.onlinebookclub.response.LoginResponse;
import com.github.aahmedae.onlinebookclub.response.MessageResponse;
import com.github.aahmedae.onlinebookclub.service.TokenAuthenticationService;
import com.github.aahmedae.onlinebookclub.service.UserBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * File: UserController.java
 * Description: Controller responsible for all user operations, including login and logout
 * Author: Asad Ahmed
 */
@RestController
@RequestMapping("api/v1/user")
public class UserController
{
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserBookService bookService;

    @Value("${security.bcrypt.strength}")
    private int bcryptPasswordEncodingStrength;

    // Invalid token response
    final static ResponseEntity<Object> INVALID_TOKEN_RESPONSE = new ResponseEntity<>(new MessageResponse("failure", "Invalid access token"), HttpStatus.BAD_REQUEST);

    // User login
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestParam("username") String username, @RequestParam("password") String password)
    {
        // TODO: Figure out how to use JWT service

        User user = userRepo.findByUsername(username);
        if (user == null)
        {
            return new ResponseEntity<>(new LoginResponse("failure", String.format("User %s does not exist", username), "0"), HttpStatus.BAD_REQUEST);
        }
        else
        {
            // decrypt and verify password
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(bcryptPasswordEncodingStrength);

            // password verified, return JWT token
            if (encoder.matches(password, user.getPassword())) {
                String token = TokenAuthenticationService.getUserTokenString(username);
                return new ResponseEntity<>(new LoginResponse("success", "successfully logged in as " + username, token), HttpStatus.ACCEPTED);
            }
            else {
                return new ResponseEntity<>(new LoginResponse("failure", "Invalid credentials", "0"), HttpStatus.UNAUTHORIZED);
            }
        }
    }

    // User registration
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object> register(@RequestParam("username") String username, @RequestParam("password") String password)
    {
        // ensure that a user with the given username does not already exist
        if (userRepo.findByUsername(username) != null) {
            return new ResponseEntity<>(new MessageResponse("failure", String.format("Username '%s' not available", username)), HttpStatus.BAD_REQUEST);
        }

        // no existing user attempt registration
        User newUser = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(bcryptPasswordEncodingStrength);

        newUser.setUsername(username);
        newUser.setPassword(encoder.encode(password));

        if (userRepo.save(newUser) != null) {
            return new ResponseEntity<>(new MessageResponse("success", String.format("User '%s' successfully created", username)), HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(new MessageResponse("failure", "There was an error in registration"), HttpStatus.BAD_REQUEST);
        }
    }

    // Creates a new user collection with the given name and description for the given user
    @RequestMapping(value = "collections/create", method = RequestMethod.POST)
    public ResponseEntity<Object> createUserCollection(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("username") String username, @RequestParam("access-token") String token)
    {
        // authenticate token
        if (!TokenAuthenticationService.validateUserToken(token, username)) {
            return INVALID_TOKEN_RESPONSE;
        }

        User user = userRepo.findByUsername(username);

        // create the new collection
        bookService.createNewCollection(user, name, description);

        return null;
    }

    // Returns the list of collections for the given user
    @RequestMapping(value = "collections/list", method = RequestMethod.GET)
    public ResponseEntity<Object> listUserCollections(@RequestParam("access-token") String token, @RequestParam("username") String username)
    {
        // authenticate token
        if (!TokenAuthenticationService.validateUserToken(token, username)) {
            return INVALID_TOKEN_RESPONSE;
        }

        // return user's book list
        User user = userRepo.findByUsername(username);
        return new ResponseEntity<>(user.getCollections(), HttpStatus.ACCEPTED);
    }
}
