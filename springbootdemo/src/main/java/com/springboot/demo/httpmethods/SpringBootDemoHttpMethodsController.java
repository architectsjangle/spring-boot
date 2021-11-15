package com.springboot.demo.httpmethods;

import com.springboot.demo.httpmethods.exceptions.BadResourceException;
import com.springboot.demo.httpmethods.exceptions.ResourceAlreadyExistsException;
import com.springboot.demo.httpmethods.exceptions.ResourceNotFoundException;
import com.springboot.demo.httpmethods.pojo.Book;
import com.springboot.demo.httpmethods.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * @author Sagar Jangle
 * @since 14th Nov 2021
 * @version 1.0
 * @apiNote This controller will demonstrate the various HTTP methods that can be used in the REST controllers.
 * Normally REST controllers supports below HTTP methods and their usage :
 * 1. GET : Normally used for READ operations.
 * Sample Calls are as below:
 *  /books  --> list all books. Pagination, sorting and filtering is used for big lists.  RESULT : 200 (OK)
 *  /books/{id} --> get specific book by id. RESULT : 200 (OK) / 404 (Not Found) - id not found or invalid.
 *
 * 2. POST : Normally used for CREATE operations.
 *  /books --> create new book. RESULT : 201 (Created) - return a Location header with a link to the
 *  newly-created resource /books/{id}.
 *  /books/id --> RESULT : Return 405 (Method Not Allowed), avoid using POST on single resource.
 *
 * 3. PUT : Normally used for Update / Replace operations.
 *  /books --> RESULT : Return 405 (Method Not Allowed), unless you want to replace every resource
 *  in the entire collection of resource - use with caution.
 *  /books/{id} --> Update a book.
 *  RESULT :    200 (OK) or 204 (No Content) - indicate successful completion of the request
 *              201 (Created) - if new resource is created and creating new resource is allowed
 *              404 (Not Found) - if id not found or invalid and creating new resource is not allowed.
 *
 * 4. PATCH : Normally used for Partial Updates.
 *   /books	--> RESULT : Return 405 (Method Not Allowed), unless you want to modify the collection itself.
 *   /books/{id} --> Partial update a book.
 *   RESULT :   200 (OK) or 204 (No Content) - indicate successful completion of the request.
 *              404 (Not Found) - if id not found or invalid.
 *
 * 5. DELETE : Normally used to Delete the individual record.
 *  /books --> RESULT : Return 405 (Method Not Allowed), unless you want to delete the whole collection - use with caution.
 *  /books/{id} --> Delete a book.
 *  RESULT :    200 (OK) or 204 (No Content) or 202 (Accepted).
 *              404 (Not Found) - if id not found or invalid.
 *
 * 6. TRACE : This HTTP method is not supported by Spring Boot. We need to use Spring Actuator for tracing the
 * Spring Boot Apps.
 * 7. HEAD : This HTTP method is also not supported by Spring Boot.
 */

@RestController
public class SpringBootDemoHttpMethodsController {

    @Autowired
    private BookService bookService;

    /** To list all Books. */
    @GetMapping("/rest/v1/books")
    public ResponseEntity<List<Book>> findAllBooks() {
        List<Book> books = (List<Book>) bookService.findAll();
        return ResponseEntity.ok(books);
    }

    /** To find a book with particular Id */
    @GetMapping("/rest/v1/books/{bookId}")
    public ResponseEntity<Optional<Book>> findBookById(@PathVariable int bookId) {
        try {
            Optional<Book> book = bookService.findById(bookId);
            return ResponseEntity.ok(book);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /** To create a new Book and will return the created Book details as well. */
    @PostMapping("/rest/v1/books")
    public ResponseEntity<Book> addBook(@RequestBody Book book) throws URISyntaxException {
        try {
            Book newBook = bookService.save(book);
            return ResponseEntity.created(new URI("/rest/v1/books/" + newBook.getId())).body(book);

        } catch (ResourceAlreadyExistsException ex) {
            // log exception first, then return Conflict (409)
            System.out.println("Exception : " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            System.out.println("Exception : " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /** PUT request is used for completely replacing resource at the given URL with the given data (FULL update). */
    @PutMapping("/rest/v1/books/{bookId}")
    public ResponseEntity<Void> updateBook(@RequestBody Book book, @PathVariable int bookId) {
        try {
            book.setId(book.getId());
            book.setBookName(book.getBookName());
            book.setBookAuthor(book.getBookAuthor());
            bookService.save(book);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            System.out.println("Exception : " + ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            System.out.println("Exception : " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /** For PARTIAL update, we use PATCH method. */
    @PatchMapping("/rest/v1/books/{bookId}")
    public ResponseEntity<Void> updateBookTitle(@RequestBody String bookAuthor, @PathVariable int bookId) {
        try {
            Optional<Book> book = bookService.findById(bookId);
            Book bookToUpdate = book.get();
            bookToUpdate.setBookAuthor(bookAuthor);
            bookService.save(bookToUpdate);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            // log exception first, then return Not Found (404)
            System.out.println("Exception : " + ex.getMessage());
            return ResponseEntity.notFound().build();
        } catch (BadResourceException ex) {
            // log exception first, then return Bad Request (400)
            System.out.println("Exception : " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /** DELETE operations are idempotent. If you DELETE a resource, it’s removed from the collection. */
    @DeleteMapping(path="/rest/v1/books/{bookId}")
    public ResponseEntity<Void> deleteBookById(@PathVariable int bookId) {
        try {
            bookService.deleteById(bookId);
            return ResponseEntity.ok().build();
        } catch (ResourceNotFoundException ex) {
            System.out.println("Exception : " + ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}