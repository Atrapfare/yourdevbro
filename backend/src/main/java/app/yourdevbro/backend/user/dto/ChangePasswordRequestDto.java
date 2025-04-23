package app.yourdevbro.backend.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequestDto {

    @NotBlank(message = "The old password is required")
    private String oldPassword;

    @NotBlank(message = "The new password is required")
    @Size(min = 8, message = "The new password must be at least 8 characters long")
    private String newPassword;
}