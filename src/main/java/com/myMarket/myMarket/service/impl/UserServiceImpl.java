package com.myMarket.myMarket.service.impl;

import com.myMarket.myMarket.dto.UserDTO;
import com.myMarket.myMarket.entity.Role;
import com.myMarket.myMarket.entity.User;
import com.myMarket.myMarket.repository.UserRepository;
import com.myMarket.myMarket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        } else {
            throw new RuntimeException("Invalid Credentials");
        }
    }

    public User register(UserDTO req) throws Exception {
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
            return userRepository.save(user);
        }
    }

    public User edit(User req) throws Exception {
        Optional<User> optUser = userRepository.findById(req.getId());
        if (optUser.isPresent()) {
            User newUser = User.builder()
                    .id(optUser.get().getId())
                    .firstname(req.getFirstname() != null && !req.getFirstname().isEmpty() ? req.getFirstname() : optUser.get().getFirstname())
                    .lastname(req.getLastname() != null && !req.getLastname().isEmpty() ? req.getLastname() : optUser.get().getLastname())
                    .username(req.getUsername() != null && !req.getUsername().isEmpty() ? req.getUsername() : optUser.get().getUsername())
                    .email(req.getEmail() != null && !req.getEmail().isEmpty() ? req.getEmail() : optUser.get().getEmail())
                    .password(optUser.get().getPassword())
                    .country(req.getCountry() != null && !req.getCountry().isEmpty() ? req.getCountry() : optUser.get().getCountry())
                    .dni(req.getDni() != null && !req.getDni().isEmpty() ? req.getDni() : optUser.get().getDni())
                    .role(optUser.get().getRole())
                    .build();

            return userRepository.save(newUser);
        } else {
            throw new Exception("The user doesn't exists");
        }
    }

    public Boolean userExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    public Boolean recoverPassword(String email, String newPassword) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean changePassword(String email, String oldPassword, String newPassword) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
//        if (optionalUser.isPresent() && passwordEncoder.matches(oldPassword, optionalUser.get().getPassword())) {
        if (optionalUser.isPresent()) {
            if (passwordEncoder.matches(oldPassword, optionalUser.get().getPassword())) {
                User userObj = optionalUser.get();
                userObj.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(userObj);
                return true;
            } else {
                throw new Exception("The password is not correct");
            }
        } else {
            throw new Exception("The user doesn't exist ");
        }
    }
}
