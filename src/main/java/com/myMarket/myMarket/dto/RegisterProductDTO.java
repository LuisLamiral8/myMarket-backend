package com.myMarket.myMarket.dto;

import com.myMarket.myMarket.entity.Category;
import com.myMarket.myMarket.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterProductDTO {
    @NotBlank(message = "The name can't be empty")
    String name;
    String description;
    @NotBlank(message = "The price can't be empty")
    Float price;
    List<Category> category;
    @NotBlank(message = "isActive can't be empty")
    boolean isActive;
    @NotBlank(message = "The seller can't be empty")
    User seller;
    @NotBlank(message = "The stock can't be empty")
    Integer stock;
}
