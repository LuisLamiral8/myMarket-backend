package com.myMarket.myMarket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class LoginDTO {
    @NotBlank(message = "The username can't be empty")
    private String username;
    @NotBlank(message = "The password can't be empty")
    private String password;
}
