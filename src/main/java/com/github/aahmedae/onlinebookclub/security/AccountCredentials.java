package com.github.aahmedae.onlinebookclub.security;

/**
 * File: AccountCredentials.java
 * Description: Class used to map request info into user credentials
 * Author: Asad Ahmed
 */
public class AccountCredentials
{
    private String username;
    private String password;

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
}
