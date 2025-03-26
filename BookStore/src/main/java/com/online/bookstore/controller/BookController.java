package com.online.bookstore.controller;

import com.online.bookstore.model.Book;
import com.online.bookstore.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books") // Base URL for all book-related endpoints
public class BookController {

    private final BookService bookService;

    // Constructor injection to initialize BookService
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Endpoint to add a new book
    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        try {
            bookService.addBook(book);
            return ResponseEntity.status(201).body("Book has been added.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error adding book: " + e.getMessage());
        }
    }

    // Endpoint to retrieve all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return books.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(books);
    }

    // Endpoint to retrieve a book by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        logger.info("Received request to fetch book with ID: {}", id);
        Optional<Book> book = bookService.getBookById(id); // Fetch the book by ID using the service layer
        if (book.isPresent()) {
            logger.info("Book found with ID {}: {}", id, book.get());
            return ResponseEntity.ok(book.get()); // Return the book if found
        } else {
            logger.warn("Book with ID {} not found", id);
            return ResponseEntity.notFound().build(); // Return 404 Not Found if the book is not found
        }
    }



    // Endpoint to update an existing book by its ID
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        try {
            bookService.updateBook(id, bookDetails);
            return ResponseEntity.ok("Book has been updated.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error updating book: " + e.getMessage());
        }
    }

    // Endpoint to delete a book by its ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try {
            bookService.deleteBook(id);
            return ResponseEntity.ok("Book has been deleted.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error deleting book: " + e.getMessage());
        }
    }
}