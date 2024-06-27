package rca.ne.java.v1.dtos.requests;

import com.sun.istack.NotNull;
import rca.ne.java.v1.annotations.ValidPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@Setter
public class CreateUserDTO {

    @javax.validation.constraints.NotNull(message =  "The first name is required")
    private String firstName;

    @javax.validation.constraints.NotNull(message = "The last name is required")
    private String lastName;

    @NotNull
    @Email(message = "Email should be a valid email")
    @Pattern(regexp = "[A-Za-z0-9\\._%+\\-]+@[A-Za-z0-9\\.\\-]+\\.[A-Za-z]{2,}", message = "The provided email is invalid")
    private String email;
    @NotNull
    @ValidPassword(message = "Password should be strong")
    private String password;

    @Pattern(regexp = "(?:\\+2507|07)\\d{8}|\\+250\\d{10}", message = "Your phone is not a valid tel we expect 2507***, or 07*** or 7***")
    private String mobile;

    @Past(message = "The date is invalid")
    private Date dob = new Date();


}
