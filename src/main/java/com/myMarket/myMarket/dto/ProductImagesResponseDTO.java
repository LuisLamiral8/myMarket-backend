package com.myMarket.myMarket.dto;

import com.myMarket.myMarket.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImagesResponseDTO {
    private Product product;
    private List<byte[]> images;
}
