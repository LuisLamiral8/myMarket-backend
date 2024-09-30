package com.myMarket.myMarket.service;

import com.myMarket.myMarket.dto.*;
import com.myMarket.myMarket.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    public Product save(RegisterProductDTO req, MultipartFile[] images) throws Exception;

    public ProductImagePaginatedResponseDTO getAllByPage(Integer pageNo, Integer itemsPage, String opt) throws IOException;

    public Product edit(Product req, MultipartFile[] images) throws Exception;

    public boolean deleteById(Long id) throws Exception;

    public ProductImagesResponseDTO findById(Long id) throws Exception;

    public Page<Product> getMyProducts(Long id, Integer pageNo, Integer itemsPage) throws Exception;

    List<ProductImageResponseDTO> getRandomProducts(Integer products) throws IOException;

    List<ImageResponseDTO> getImagesFileById(Long id) throws Exception;

    ProductImagePaginatedResponseDTO getAllByName(Integer pageNo, Integer itemsPage, String opt, String searchReq) throws IOException;
}
