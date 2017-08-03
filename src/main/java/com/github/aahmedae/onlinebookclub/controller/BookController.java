// BookController.java

package com.github.aahmedae.onlinebookclub.controller;

import com.github.aahmedae.onlinebookclub.response.BookListResponse;
import com.github.aahmedae.onlinebookclub.response.BookResponse;
import com.github.aahmedae.onlinebookclub.response.MessageResponse;
import com.github.aahmedae.onlinebookclub.service.GoogleBooksService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * File: BookController
 * Description: Controller responsible for dealing with requests for books and book collections
 * Author: Asad Ahmed
 */
@RestController
@RequestMapping("/api/v1/book")
public class BookController
{
    // Google Books service for getting info from Google Books API
    @Autowired
    GoogleBooksService googleBooksService;

    // string values for the params that indicate on how the lists should be sorted
    private static final String SORT_KEY_TITLE = "title";
    private static final String SORT_KEY_RATING = "rating";

    // Search for a list of books by the given title search string
    @RequestMapping(value = "/public/search", method = RequestMethod.GET)
    public ResponseEntity<Object> searchBooksByTitle(@RequestParam("title") String title, @RequestParam(value = "sortby", required = false) String sortKey)
    {
        List<BookResponse> books = googleBooksService.searchForBooks(title);
        List<BookResponse> sortedBooks = null;

        if (books != null)
        {
            // apply sort if requested
            if (sortKey != null)
            {
                switch (sortKey)
                {
                    case SORT_KEY_RATING:
                        sortedBooks = books.stream().sorted((b1, b2) -> (int)((b2.getRating() - b1.getRating()) * 10.0f)).collect(Collectors.toList());
                        break;

                    case SORT_KEY_TITLE:
                        sortedBooks = books.stream().sorted((b1, b2) -> b1.getTitle().compareTo(b2.getTitle())).collect(Collectors.toList());
                        break;

                    default:
                        break;
                }
            }

            BookListResponse response = new BookListResponse("success", (sortedBooks == null ? books : sortedBooks));
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        }
        else
            {
            MessageResponse response = new MessageResponse("failure", "Failed to process request");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
