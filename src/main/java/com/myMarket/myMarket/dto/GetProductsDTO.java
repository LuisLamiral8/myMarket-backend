package com.myMarket.myMarket.dto;

import com.myMarket.myMarket.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetProductsDTO {
    private List<Product> products;
    Integer totalProducts;
}
