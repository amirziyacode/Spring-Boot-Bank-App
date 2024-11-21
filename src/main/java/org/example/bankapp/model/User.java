package org.example.bankapp.model;


import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class User {

    private String username;
    private String password;
    private Long balance;
    private UUID accountNumber;

}