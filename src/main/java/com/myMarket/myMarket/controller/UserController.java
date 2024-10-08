package com.myMarket.myMarket.controller;


import com.myMarket.myMarket.dto.AuthResponse;
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


    @PostMapping(value = "edit")
    public ResponseEntity<Object> edit(@RequestBody User req) {
        try {
            AuthResponse response = userService.edit(req);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @GetMapping(value = "userExists")
    public ResponseEntity<Object> userExists(@RequestParam String email) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.userExists(email));
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
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

    @PostMapping(value ="deleteByUsername")
    public ResponseEntity<Object> deleteByUsername(@RequestParam String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.deleteByUsername(username));
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @GetMapping(value ="getByUsername")
    public ResponseEntity<Object> getByUsername(@RequestParam String username){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userService.getByUsername(username));
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }
}
