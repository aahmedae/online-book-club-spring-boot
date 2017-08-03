package com.github.aahmedae.onlinebookclub.response;

/**
 * File: LoginResponse.java
 * Description: The response object when the user attempts a login
 * Author: Asad Ahmed
 */
public class LoginResponse
{
    // The status of the login attempt. success of failure.
    private String status;

    // A message further describing the status
    private String message;

    // The login token to use for this user
    private String usertoken;

    public LoginResponse()
    {
        this("Failure", "User does not exist", "0");
    }

    public LoginResponse(String status, String message, String usertoken)
    {
        this.status = status;
        this.message = message;
        this.usertoken = usertoken;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsertoken() {
        return usertoken;
    }

    public void setUsertoken(String usertoken) {
        this.usertoken = usertoken;
    }
}
