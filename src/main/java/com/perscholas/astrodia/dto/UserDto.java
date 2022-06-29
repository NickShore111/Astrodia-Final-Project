package com.perscholas.astrodia.dto;

import com.perscholas.astrodia.validations.PasswordMatches;
import com.perscholas.astrodia.validations.ValidEmail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@PasswordMatches
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    private String password;
    private String matchingPassword;

//    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;
}
