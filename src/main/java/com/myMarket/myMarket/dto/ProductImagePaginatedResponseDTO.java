package com.myMarket.myMarket.dto;

import com.myMarket.myMarket.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImagePaginatedResponseDTO {

    List<ProductImageResponseDTO> mainObj;
    private Page<Product> pagingInfo;
}
