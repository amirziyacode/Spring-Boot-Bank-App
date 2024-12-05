package org.example.bankapp.repo;

import org.example.bankapp.model.TransactionsBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionsBankRepo extends JpaRepository<TransactionsBank, Integer> { }
