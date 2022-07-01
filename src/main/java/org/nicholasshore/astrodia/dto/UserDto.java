package org.nicholasshore.astrodia.dto;

import org.nicholasshore.astrodia.validators.PasswordMatches;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@PasswordMatches
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @NotNull
    @NotEmpty(message="{NotEmpty.User.Missing}")
    private String firstName;

    @NotNull
    @NotEmpty(message="{NotEmpty.User.Missing}")
    private String lastName;

    @NotNull
    @NotEmpty(message="{NotEmpty.User.Missing}")
    private String password;
    private String matchingPassword;

    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$", message = "{Pattern.User.Email}")
    @NotNull
    @NotEmpty(message = "{NotEmpty.User.Missing}")
    private String email;
}
