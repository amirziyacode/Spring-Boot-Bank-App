package org.example.bankapp.repo;

import org.example.bankapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByAccountNumber(UUID accountNumber);
}
