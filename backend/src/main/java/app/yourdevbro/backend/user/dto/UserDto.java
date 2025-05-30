package app.yourdevbro.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String preName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dateOfBirth;

}