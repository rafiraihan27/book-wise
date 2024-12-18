package com.tubesbookwise.Service;

import com.tubesbookwise.Repository.UserRepository;
import com.tubesbookwise.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {

        @Autowired
        public UserRepository userRepository;
        public Long getJumlahUserNow() {
            return userRepository.count();
        }

        @Autowired
        private BookRepository bookRepository;

        public long  getStatisticBook(){
            return bookRepository.count();
        }
}