// GoogleBooksService.java

package com.github.aahmedae.onlinebookclub.service;

import com.github.aahmedae.onlinebookclub.response.BookResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * File: GoogleBooksService.java
 * Description: Service that gets information from Google Books API
 * Author: Asad Ahmed
 */
@Service
public class GoogleBooksService
{
    // The Google Books API Key
    final static String API_KEY = "AIzaSyDcjN1bF7P-LvYFZpHgUDbEfpXPzrVlcpE";

    // The Google Books Search URL
    final static String GOOGLE_BOOKS_SEARCH_URL = "https://www.googleapis.com/books/v1/volumes";

    // The max number of results to process
    @Value("${googlebooks.api.maxresults}")
    private int maxSearchResults;

    /*
        To see the JSON directly:
        https://www.googleapis.com/books/v1/volumes?q=Harry+Potter&langRestrict=en&key=AIzaSyDcjN1bF7P-LvYFZpHgUDbEfpXPzrVlcpE
    */

    // Search for the given book based on title
    public List<BookResponse> searchForBooks(String title)
    {
        List<BookResponse> books = new ArrayList<>();

        try
        {
            HttpResponse<JsonNode> jsonResponse = Unirest.get(GOOGLE_BOOKS_SEARCH_URL)
                    .header("accept", "application/json")
                    .queryString("key", API_KEY).queryString("q", title).queryString("langRestrict", "en").queryString("maxResults", maxSearchResults)
                    .asJson();

            if (jsonResponse.getStatus() == 200)
            {
                // parse json and extract book info into array of book response objects
                JSONArray bookJsonArray = jsonResponse.getBody().getObject().getJSONArray("items");

                for (int i = 0; i < bookJsonArray.length(); i++)
                {
                    JSONObject bookJsonObject = bookJsonArray.getJSONObject(i);
                    BookResponse book = parseBookJSON(bookJsonObject);
                    books.add(book);
                }

                return books;
            }
            else
            {
                System.err.println("Google books API returned status: " + jsonResponse.getStatus());
                return null;
            }
        }
        catch (UnirestException ex)
        {
            System.err.println("Error in making request to Google Books");
            System.err.println(ex.getLocalizedMessage());
            return null;
        }
    }

    // Parses the bookJSON object into a book response object
    private BookResponse parseBookJSON(JSONObject json)
    {
        BookResponse book = new BookResponse();

        JSONObject volumeInfo = json.getJSONObject("volumeInfo");

        // easily accessible properties
        book.setId(json.getString("id"));
        book.setTitle(volumeInfo.getString("title"));

        if (volumeInfo.has("averageRating")) {
            book.setRating((float) volumeInfo.getDouble("averageRating"));
        }

        if (volumeInfo.has("description")) {
            book.setSynopsis(volumeInfo.getString("description"));
        }

        if (volumeInfo.has("publisher")) {
            book.setPublisher(volumeInfo.getString("publisher"));
        }

        // list of authors
        if (volumeInfo.has("authors")) {
            List<String> authors = new ArrayList<>();
            JSONArray authorsArray = volumeInfo.getJSONArray("authors");
            for (int i = 0; i < authorsArray.length(); i++) {
                authors.add(authorsArray.getString(i));
            }
            book.setAuthors(authors);
        }

        // cover image as base64 string
        if (volumeInfo.has("imageLinks"))
        {
            JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
            InputStream is = null;

            try {
                URL imageURL = new URL(imageLinks.getString("thumbnail"));
                is = imageURL.openStream();

                byte[] data = IOUtils.toByteArray(is);
                book.setCoverImage(Base64.getEncoder().encodeToString(data));
            } catch (MalformedURLException ex) {
                System.err.println("Invalid URL: " + imageLinks.getString("thumbnail"));
                return null;
            } catch (IOException ex) {
                System.err.println("Failed to process image data when parsing book cover URL");
                return null;
            }
        }

        return book;
    }
}
