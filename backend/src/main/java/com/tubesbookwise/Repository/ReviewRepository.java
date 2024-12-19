package com.tubesbookwise.Repository;

import com.tubesbookwise.Models.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    Page<Review> findByBookId(String bookId, Pageable pageable);
    Page<Review> findAll(Pageable pageable);
}

