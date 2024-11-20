package org.example.bankapp.service;
import java.util.UUID;

public interface BankService{

    Long viewBalance(UUID accountId);
    void deposit(UUID accountId, Long amount);
    void withdraw(UUID accountId, Long amount);
    void transfer(UUID from,UUID to, Long amount);
}
