package com.tubesbookwise.Repository;

import com.tubesbookwise.Models.TransactionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionItemRepository extends JpaRepository<TransactionItem, String> {
}
