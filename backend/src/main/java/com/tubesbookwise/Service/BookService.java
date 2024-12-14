package com.tubesbookwise.Service;

import com.tubesbookwise.Models.Book;
import com.tubesbookwise.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(String search, String category, Integer years) {
        // Example: Implement search logic based on parameters if needed.
        // For now, return all books.
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(UUID id) {
        return bookRepository.findById(id);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(UUID id, Book updatedBook) {
        return bookRepository.findById(id).map(existingBook -> {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setCategory(updatedBook.getCategory());
            existingBook.setYear(updatedBook.getYear());
            existingBook.setDescription(updatedBook.getDescription());
            existingBook.setImage(updatedBook.getImage());
            existingBook.setQuota(updatedBook.getQuota());
            existingBook.setRackNumber(updatedBook.getRackNumber());
            existingBook.setIsbn(updatedBook.getIsbn());
            existingBook.setLanguage(updatedBook.getLanguage());
            existingBook.setAvailableCopies(updatedBook.getAvailableCopies());
            existingBook.setLateFee(updatedBook.getLateFee());
            existingBook.setCanBorrow(updatedBook.isCanBorrow());
            existingBook.setRating(updatedBook.getRating());
            return bookRepository.save(existingBook);
        }).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
}
