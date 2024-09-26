package com.myMarket.myMarket.dto;

import com.myMarket.myMarket.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageResponseDTO {
    private Product product;
    private byte[] image;
}
