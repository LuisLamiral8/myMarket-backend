package com.myMarket.myMarket.service;

import com.myMarket.myMarket.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    public String getToken(UserDetails user);

    String getUsernameFromToken(String token);

    boolean isTokenValid(String token, UserDetails userDetails);
}
