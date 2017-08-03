# Spring Boot Online Book Club REST system
### A Java 8 REST backend system built using Spring Boot

#### Description
A simple REST backend service for impleneting an online social book club. Users will be able to sign up, search for books, add friends, create their book collections, and check out book collections created by others. The system utlises the Google Books API for the book information.

__[Under Development]__

Currently the public endpoint for searching for books using Google Books API works:

* #### Book Search:
<table>
  <tr>
    <th>Endpoint:</th>
    <td colspan = "2">https://online-book-club.herokuapp.com/api/v1/book/public/search</td>
   </tr>
   <tr>
    <th>Parameters:</th>
    <td colspan = "2"></td>
   </tr>
   <tr>
    <td>title</td>
    <td>Required</td>
    <td>The title of the book to search for</td>
   </tr>
   <tr>
    <td>sortby</td>
    <td>Optional</td>
    <td>The sorting method to use for filtering the JSON array. Possible Values: title, rating</td>
   </tr>
   <tr>
    <th>JSON Response:</th>
    <td colspan = "2"></td>
   </tr>
   <tr>
    <td>result</td>
    <td>String</td>
    <td>A message that can be either 'success' or 'failure'</td>
   </tr>
   <tr>
    <td>books</td>
    <td>Array</td>
    <td>An array of JSON nodes that represent information on a book</td>
   </tr>
   <tr>
    <td>Book JSON:</td>
    <td colspan = "2"></td>
   </tr>
   <tr>
    <td>id</td>
    <td>String</td>
    <td>An identifier string for this book</td>
   </tr>
   <tr>
    <td>title</td>
    <td>String</td>
    <td>The title of the book</td>
   </tr>
   <tr>
    <td>authors</td>
    <td>Array</td>
    <td>An array of stings representing the list of authors for this book</td>
   </tr>
   <tr>
    <td>publisher</td>
    <td>String</td>
    <td>The publisher of the book/td>
   </tr>
   <tr>
    <td>synopsis</td>
    <td>String</td>
    <td>The summary of the book theme and plot</td>
   </tr>
   <tr>
    <td>rating</td>
    <td>Float</td>
    <td>rating of the book out of 5</td>
   </tr>
   <tr>
    <td>coverImage</td>
    <td>Base64 String</td>
    <td>A base 64 encoded string that represents the book's cover image</td>
   </tr>
</table>

__Try it out: [Search for book by title 'Wuthering Heights'](https://online-book-club.herokuapp.com/api/v1/book/public/search?title=Wuthering+Heights) 

__[Features Under Development]:__

* Security: Spring security coupled with JWT for token-based authentication.
* User Management: Login and registration
* Social Features: Creating book collections, adding friends, rating other collections, etc...
