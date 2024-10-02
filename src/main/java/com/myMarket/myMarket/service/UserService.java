package com.myMarket.myMarket.service;

import com.myMarket.myMarket.dto.AuthResponse;
import com.myMarket.myMarket.dto.UserDTO;
import com.myMarket.myMarket.entity.User;

public interface UserService {
    public User register(UserDTO user) throws Exception;

    public User login(String email, String password);

    public AuthResponse edit(User user) throws Exception;

    public Boolean userExists(String email);

    public Boolean recoverPassword(String email, String password);

    Boolean changePassword(String email, String oldPassword, String newPassword) throws Exception;

    User getByUsername(String username) throws Exception;

    Boolean deleteByUsername(String username) throws Exception;
}
