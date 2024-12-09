package net.yazin.stonks.User.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.yazin.stonks.Common.model.enums.UserRole;
import net.yazin.stonks.User.model.dto.UserDTO;

@Entity
@Table(name = "STONK_USERS")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private int userId;

    private String fullName;

    @Column(unique = true) private String username;

    private UserRole userRole;

    private String userIdOnAuthServer;

    @Column(unique = true) private String email;

    private long dateJoined;

    private void setDateJoined(long dateJoined) {
        this.dateJoined = dateJoined;
    }

    public static User getUser(UserDTO userDTO){
        User user = new User();
        user.setFullName(userDTO.getFullName());
        user.setUsername(userDTO.getUsername());
        user.setUserRole(userDTO.getUserRole());
        user.setEmail(userDTO.getEmail());

        user.setDateJoined(System.currentTimeMillis());
        return user;
    }
}
