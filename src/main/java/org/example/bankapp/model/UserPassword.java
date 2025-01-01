package org.example.bankapp.model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPassword {
    @NotBlank
    @NotNull
    private String oldPassword;
    @NotBlank
    @NotNull
    private String newPassword;
    @NotBlank
    @NotNull
    private String confirmPassword;
}
