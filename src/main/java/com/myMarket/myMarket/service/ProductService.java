package com.myMarket.myMarket.service;

import com.myMarket.myMarket.dto.GetProductsDTO;
import com.myMarket.myMarket.dto.RegisterProductDTO;
import com.myMarket.myMarket.entity.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public Product save(RegisterProductDTO req) throws Exception;
    public GetProductsDTO getAll ();

    public GetProductsDTO getAllByPage (Integer pageNo, Integer itemsPage);

    public Product edit(Product req) throws Exception;

    public boolean deleteById(Long id) throws Exception;

    public Product findById(Long id) throws Exception;

    public Page<Product> getMyProducts(Long id, Integer pageNo, Integer itemsPage) throws Exception;

    List<Product> getRandomProducts(Integer products);
}
