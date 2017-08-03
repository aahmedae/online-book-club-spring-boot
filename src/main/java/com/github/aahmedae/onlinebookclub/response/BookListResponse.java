// BookListResponse.java

package com.github.aahmedae.onlinebookclub.response;

import java.util.ArrayList;
import java.util.List;

/**
 * File: BookListResponse.java
 * Description: The response object that will be returned for requesting a result that includes a list of books
 * Author: Asad Ahmed
 */
public class BookListResponse
{
    // The result message of the request
    private String result;

    // The list of books in the response
    private List<BookResponse> books;

    public BookListResponse()
    {
        this("NA", new ArrayList<>());
    }

    public BookListResponse(String result, List<BookResponse> books)
    {
        this.result = result;
        this.books = books;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<BookResponse> getBooks() {
        return books;
    }

    public void setBooks(List<BookResponse> books) {
        this.books = books;
    }
}
