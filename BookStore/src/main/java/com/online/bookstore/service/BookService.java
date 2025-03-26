package com.online.bookstore.service;

import com.online.bookstore.model.Book;
import com.online.bookstore.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Marks this class as a Spring service component
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) { // Constructor injection for BookRepository
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) { // Add a new book to the repository
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() { // Retrieve all books from the repository
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) { // Retrieve a book by its ID
        return bookRepository.findById(id);
    }

    public Book updateBook(Long id, Book bookDetails) { // Update an existing book by its ID
        return bookRepository.findById(id).map(book -> {
            book.setTitle(bookDetails.getTitle()); // Update title
            book.setAuthor(bookDetails.getAuthor()); // Update author
            book.setPrice(bookDetails.getPrice()); // Update price
            book.setPublishedDate(bookDetails.getPublishedDate()); // Update published date
            return bookRepository.save(book); // Save updated book
        }).orElseThrow(() -> new RuntimeException("Book not found")); // Throw exception if book not found
    }

    public void deleteBook(Long id) { // Delete a book by its ID
        bookRepository.deleteById(id);
    }
}