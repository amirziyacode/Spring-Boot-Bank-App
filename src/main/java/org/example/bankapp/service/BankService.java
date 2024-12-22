package org.example.bankapp.service;
import org.example.bankapp.model.User;

import java.util.UUID;

public interface BankService{

    Double viewBalance(UUID accountId);
    User deposit(UUID accountId, Double amount);
    User withdraw(UUID accountId, Double amount);
    User transfer(UUID from, UUID to, Double amount);
}
