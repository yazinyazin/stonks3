package net.yazin.stonks.User.rest;

import lombok.RequiredArgsConstructor;
import net.yazin.stonks.User.model.dto.UserDTO;
import net.yazin.stonks.User.model.entity.User;
import net.yazin.stonks.User.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("add")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<Void> add(@RequestBody UserDTO userDTO){
        userService.addUser(userDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("all")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(userService.getUsers());
    }
}
