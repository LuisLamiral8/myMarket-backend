package com.myMarket.myMarket.controller;


import com.myMarket.myMarket.dto.LoginDTO;
import com.myMarket.myMarket.dto.UserDTO;
import com.myMarket.myMarket.entity.User;
import com.myMarket.myMarket.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserDTO req) {
        try {
            User response = userService.register(req);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @PostMapping(value = "login")
    public ResponseEntity<Object> register(@RequestBody LoginDTO req) {
        try {
            User response = userService.login(req.getEmail(), req.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.getMessage());
        }

    }

    @PostMapping(value = "edit")
    public ResponseEntity<Object> edit(@RequestBody User req) {
        try {
            User response = userService.edit(req);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @GetMapping(value = "userExists")
    public ResponseEntity<Boolean> userExists(@RequestParam String email) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.userExists(email));
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @PostMapping(value = "recoverPassword")
    public ResponseEntity<Boolean> recoverPassword(@RequestParam String email, @RequestParam String newPassword) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.recoverPassword(email, newPassword));
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }

    @PostMapping(value = "changePassword")
    public ResponseEntity<Object> changePassword(@RequestParam String email, @RequestParam String oldPassword, @RequestParam String newPassword) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.changePassword(email, oldPassword, newPassword));
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @PostMapping(value ="deleteById")
    public ResponseEntity<Object> deleteById(@RequestParam Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.deleteById(id));
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }
}
