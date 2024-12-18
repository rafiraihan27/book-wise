package com.tubesbookwise.Service;

import com.tubesbookwise.Models.Book;
import com.tubesbookwise.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StatisticService {

    @Autowired
    private BookRepository bookRepository;

    public long  getStatisticBook(){
        return bookRepository.count();
    }
}