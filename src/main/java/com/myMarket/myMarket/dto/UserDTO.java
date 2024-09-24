package com.myMarket.myMarket.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @NotBlank(message = "The firstname can't be empty")
    private String firstname;
    @NotBlank(message = "The lastname can't be empty")
    private String lastname;
    @NotBlank(message ="The username can't be empty")
    private String username;
    @NotBlank(message = "The email can't be empty")
    private String email;
    @NotBlank(message = "The password can't be empty")
    private String password;
    @NotBlank(message = "The country can't be empty")
    private String country;
    @NotBlank(message = "The dni can't be empty")
    private String dni;

}
