package net.yazin.stonks.User.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.yazin.stonks.Common.model.enums.UserRole;

@NoArgsConstructor
@Getter
public class UserDTO {

    @NotNull private String fullName;

    @NotNull private String username;

    @NotNull private String password;

    @NotNull private UserRole userRole;

    @NotNull @Email private String email;

}
