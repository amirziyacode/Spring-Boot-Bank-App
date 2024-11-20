package org.example.bankapp.model;


import lombok.Data;

import java.util.UUID;

@Data
public class User {

    private String username;
    private String password;
    private Long balance;
    private UUID accountNumber;

}
