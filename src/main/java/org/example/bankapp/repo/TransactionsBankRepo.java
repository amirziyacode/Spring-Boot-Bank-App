package org.example.bankapp.repo;

import org.example.bankapp.model.TransactionsBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsBankRepo extends JpaRepository<TransactionsBank, Long> { }
