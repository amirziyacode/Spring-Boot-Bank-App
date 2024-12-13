package org.example.bankapp.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPassword {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
