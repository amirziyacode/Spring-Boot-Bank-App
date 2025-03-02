package org.example.bankapp.repository;

import org.example.bankapp.model.TransactionsBank;
import org.example.bankapp.repo.TransactionsBankRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TransactionsBankRepoTest {

    @Autowired
    private  TransactionsBankRepo transactionsBankRepo;

    @Test
    void findByUserId() {
        // mock user and transaction
        TransactionsBank transactionsBank = TransactionsBank.builder()
                .createdDate(LocalDateTime.now())
                .methodName("methodName")
                .userId(3)
                .accountNumberTo(UUID.randomUUID())
                .accountNumberFrom(UUID.randomUUID())
                .amount(100.0)
                .build();

        transactionsBankRepo.save(transactionsBank);

        List<TransactionsBank> byUserId = transactionsBankRepo.findByUserId(3);

        assertThat(byUserId).hasSize(1);
        assertThat(byUserId.get(0)).isEqualTo(transactionsBank);
    }
}