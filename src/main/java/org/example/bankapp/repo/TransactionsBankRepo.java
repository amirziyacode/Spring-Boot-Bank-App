package org.example.bankapp.repo;

import org.example.bankapp.model.TransactionsBank;
import org.example.bankapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionsBankRepo extends JpaRepository<TransactionsBank, Long> {
    List<TransactionsBank> findByUserId(Long userId);
}
