package app.yourdevbro.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestUpdateUserDto {

    private String preName;

    private String lastName;

    @Email(message = "Invalid email format")
    private String email;

    @Size(min = 10, max = 20, message = "Phone number must be between 10 and 20 digits")
    private String phone;

    private LocalDate dateOfBirth;

}