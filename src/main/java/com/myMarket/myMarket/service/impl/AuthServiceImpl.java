package com.myMarket.myMarket.service.impl;

import com.myMarket.myMarket.dto.AuthResponse;
import com.myMarket.myMarket.dto.LoginDTO;
import com.myMarket.myMarket.dto.UserDTO;
import com.myMarket.myMarket.entity.Role;
import com.myMarket.myMarket.entity.User;
import com.myMarket.myMarket.repository.UserRepository;
import com.myMarket.myMarket.service.AuthService;
import com.myMarket.myMarket.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginDTO req) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
        UserDetails user = userRepository.findByUsername(req.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public AuthResponse register(UserDTO req) throws Exception {
        Optional<User> foundWithEmail = userRepository.findByEmail(req.getEmail());
        Optional<User> foundWithUsername = userRepository.findByUsername(req.getUsername());
        Optional<User> foundWithDni = userRepository.findByDni(req.getDni());
        if (foundWithEmail.isPresent()) {
            throw new Exception("The email exists");
        } else if (foundWithUsername.isPresent()) {
            throw new Exception("The username exists");
        } else if (foundWithDni.isPresent()) {
            throw new Exception("The Dni exists");
        } else {
            User user = User.builder()
                    .firstname(req.getFirstname())
                    .lastname(req.getLastname())
                    .username(req.getUsername())
                    .email(req.getEmail())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .country(req.getCountry())
                    .dni(req.getDni())
                    .role(Role.USER)
                    .build();
            userRepository.save(user);
            return AuthResponse.builder()
                    .token(jwtService.getToken(user))
                    .build();
        }
    }
}
