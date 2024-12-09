package net.yazin.stonks.User.service;

import jakarta.validation.Valid;
import net.yazin.stonks.User.model.dto.UserDTO;
import net.yazin.stonks.User.model.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    @Transactional
    void addUser(@Valid UserDTO userDTO);

    List<User> getUsers();
}
