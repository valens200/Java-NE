package com.templates.valens.v1.dtos.requests;

import com.sun.istack.NotNull;
import com.templates.valens.v1.annotations.ValidPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class CreateUserDTO {
    @NotNull
    private String userName;
    @NotNull
    @Email(message = "Email should be a valid email")
    private String email;
    @NotNull
    @ValidPassword(message = "Password should be strong")
    private String password;

}
