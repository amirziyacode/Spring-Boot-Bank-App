package org.example.bankapp.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Transaction")
public class TransactionsBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String methodName;
    private UUID accountNumberTo;
    private UUID accountNumberFrom;
    private double amount;
    @CreationTimestamp
    private LocalDateTime createdDate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false,referencedColumnName = "id")
    private User user;
}
