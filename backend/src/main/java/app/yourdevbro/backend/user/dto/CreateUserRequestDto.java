package app.yourdevbro.backend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequestDto {

    private String preName;
    private String lastName;
    private String email;
    private String phone;

}