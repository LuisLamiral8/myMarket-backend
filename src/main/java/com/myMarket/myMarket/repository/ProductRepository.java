package com.myMarket.myMarket.repository;

import com.myMarket.myMarket.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findALlBySellerId(Long id);

    Page<Product> findAllBySellerId(Pageable page, Long id);


    @Query(value = "SELECT * FROM product ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Product> findRandomProducts(@Param("count") Integer count);
}
