package lt.codeacademy.blog_spring.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRegistrationDTO {

    @NotBlank
    private String username;

    @Size(min = 5, max = 30)
    private String password;

    @Size(min = 5, max = 30)
    private String passwordConfirm;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}
