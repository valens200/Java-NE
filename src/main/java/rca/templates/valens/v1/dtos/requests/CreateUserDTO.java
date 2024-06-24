package rca.templates.valens.v1.dtos.requests;

import com.sun.istack.NotNull;
import rca.templates.valens.v1.annotations.ValidPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class CreateUserDTO {
    @javax.validation.constraints.NotNull(message =  "The first name is required")
    private String firstName;

    @javax.validation.constraints.NotNull(message = "The last name is required")
    private String lastName;

    @NotNull
    @Email(message = "Email should be a valid email")
    private String email;
    @NotNull
    @ValidPassword(message = "Password should be strong")
    private String password;

}
