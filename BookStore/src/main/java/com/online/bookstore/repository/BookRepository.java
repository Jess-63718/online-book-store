package com.online.bookstore.repository;

import com.online.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marks this as a repository component
public interface BookRepository extends JpaRepository<Book, Long> {
}
