package com.myMarket.myMarket.repository;

import com.myMarket.myMarket.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findALlBySellerId(Long id);
}
