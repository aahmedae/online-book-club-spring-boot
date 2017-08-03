// MessageResponse.java

package com.github.aahmedae.onlinebookclub.response;

/**
 * File: MessageResponse.java
 * Description: Simple response object that contains a success / fail status and a message
 * Author: Asad Ahmed
 */
public class MessageResponse
{
    private String status;
    private String message;

    public MessageResponse(String status, String message)
    {
        this.status = status;
        this.message = message;
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
}
