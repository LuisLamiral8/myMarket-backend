package com.myMarket.myMarket.controller;

import com.myMarket.myMarket.dto.AuthResponse;
import com.myMarket.myMarket.dto.LoginDTO;
import com.myMarket.myMarket.dto.UserDTO;
import com.myMarket.myMarket.entity.User;
import com.myMarket.myMarket.service.AuthService;
import com.myMarket.myMarket.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping(value = "register")
    public ResponseEntity<Object> register(@RequestBody @Valid UserDTO req) {
        try {
//            User response = userService.register(req);
//            return ResponseEntity.status(HttpStatus.OK).body(response);
            return ResponseEntity.status(HttpStatus.OK).body(authService.register(req));

        } catch (Exception error) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.getMessage());
        }
    }

    @PostMapping(value = "login")
    public ResponseEntity<Object> login(@RequestBody LoginDTO req) {
        try {
//            User response = userService.login(req.getUsername(), req.getPassword());
//            return ResponseEntity.status(HttpStatus.OK).body(response);
            return ResponseEntity.status(HttpStatus.OK).body(authService.login(req));
        } catch (Exception error) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.getMessage());
        }

    }

}
