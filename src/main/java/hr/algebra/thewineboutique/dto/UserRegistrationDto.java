package hr.algebra.thewineboutique.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserRegistrationDto {
    private String username;
    private String password;
    private String confirmPassword;
}
