package net.yazin.stonks.User.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.yazin.stonks.User.data.repository.UserRepository;
import net.yazin.stonks.User.model.dto.UserDTO;
import net.yazin.stonks.User.model.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final AuthServerService authServerService;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addUser(@Valid UserDTO userDTO){
        User user = userRepository.saveAndFlush(User.getUser(userDTO));
        String userIdOnAuthServer = authServerService.addUserToAuthServer(user, userDTO.getPassword());
        user.setUserIdOnAuthServer(userIdOnAuthServer);
        userRepository.save(user);
    }

    @Override
    public List<User> getUsers(){
        return userRepository.findAll();
    }


    //deactivate, reactivate, logout, etc.

}
