package com.tubesbookwise.Service;

import com.tubesbookwise.Service.BookService;
import com.tubesbookwise.Service.User.UserService;
import com.tubesbookwise.dto.Review.ReviewDTO;
import com.tubesbookwise.dto.Review.ReviewRequest;
import com.tubesbookwise.dto.Review.ReviewContent;
import com.tubesbookwise.Models.Book;
import com.tubesbookwise.Models.Review;
import com.tubesbookwise.Models.User;
import com.tubesbookwise.Repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewRepository reviewRepository;

    // Method to get reviews with optional filters
    public List<ReviewDTO> getReview(String bookId, Integer max) {
        // Default to 5 reviews if max is not provided
        if (max == null) {
            max = 5;
        }

        // PageRequest to limit the number of reviews and sort by date in descending order (most recent first)
        PageRequest pageRequest = PageRequest.of(0, max);

        // Fetch the reviews from the repository, using pagination
        Page<Review> reviewPage = (bookId != null)
                ? reviewRepository.findByBookId(bookId, pageRequest)
                : reviewRepository.findAll(pageRequest);

        // Map the Review entities to ReviewDTO objects
        return reviewPage.getContent().stream()
                .map(review -> new ReviewDTO(
                        review.getId(),
                        review.getBook().getTitle(),
                        review.getAuthor().getName(),
                        review.getDate(),
                        review.getRating(),
                        review.getContent()
                ))
                .collect(Collectors.toList());
    }


    public ResponseEntity<?> submitReview(ReviewRequest reviewRequest) {
        String bookId = reviewRequest.getBookId();
        String userId = reviewRequest.getReview().getAuthorId();

        // Check if the book exists
        Optional<Book> bookOptional = bookService.getBookById(bookId);
        if (bookOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "Book not found with id: " + bookId)
            );
        }

        // Check if the user exists
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("message", "User not found with id: " + userId)
            );
        }

        // Proceed with the review logic (e.g., saving the review to the database)
        ReviewContent reviewContent = reviewRequest.getReview();
        String authorId = reviewContent.getAuthorId();
        double rating = reviewContent.getRating();
        String content = reviewContent.getContent();

        // Create the Review entity
        Review review = new Review();
        review.setBook(bookOptional.get());
        review.setAuthor(userOptional.get());
        review.setDate(LocalDateTime.now());
        review.setRating(rating);
        review.setContent(content);

        // save to database
        reviewRepository.save(review);

        // response
        Map<String, Object> response = new HashMap<>();
        response.put("bookId", bookId);
        response.put("author", authorId);
        response.put("rating", rating);
        response.put("content", content);

        return ResponseEntity.ok().body(response);
    }
}
