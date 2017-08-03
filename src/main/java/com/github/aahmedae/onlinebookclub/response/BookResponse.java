// BookResponse.java

package com.github.aahmedae.onlinebookclub.response;

import java.util.ArrayList;
import java.util.List;

/**
 * File: BookResponse.java
 * Description: The response object that encapsulates the information for a book
 * Author: Asad Ahmed
 */
public class BookResponse
{
    // The ID of the book (not ISBN)
    private String id;

    // The title of the book
    private String title;

    // The list of authors for the book
    private List<String> authors;

    // The publisher of the book
    private String publisher;



    // The summary of the book
    private String synopsis;

    // The rating of the book out of 5
    private float rating;

    // The image of the book cover encoded as base64
    private String coverImage;

    public BookResponse()
    {
        this ("NA", "NA", new ArrayList<>(), "NA", "NA", 0.0f, "0");
    }

    public BookResponse(String id, String title, List<String> authors, String publisher, String synopsis, float rating, String coverImage)
    {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
        this.synopsis = synopsis;
        this.rating = rating;
        this.coverImage = coverImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
