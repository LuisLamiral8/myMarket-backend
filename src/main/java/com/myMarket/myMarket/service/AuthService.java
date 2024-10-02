package com.myMarket.myMarket.service;

import com.myMarket.myMarket.dto.AuthResponse;
import com.myMarket.myMarket.dto.LoginDTO;
import com.myMarket.myMarket.dto.UserDTO;

public interface AuthService {
    public AuthResponse login(LoginDTO req);
    public AuthResponse register(UserDTO req) throws Exception;

}
