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
    Page<Product> findAllBySellerId(Pageable page, Long id);

    List<Product> findAllBySellerId(Long id);

    //FindAllBy Ordenado por..
    Page<Product> findAllByOrderByNameAsc(Pageable page);
    Page<Product> findAllByOrderByPriceAsc(Pageable page);
    Page<Product> findAllByOrderByDescriptionAsc(Pageable page);
    @Query("SELECT p FROM Product p JOIN p.category c ORDER BY c.name ASC")
    Page<Product> findAllByOrderByCategoryNameAsc(Pageable page);


    //FindAll by firstname, ordenado por..
    Page<Product> findAllByNameContainingOrderByNameAsc(Pageable page, String name);
    Page<Product> findAllByNameContainingOrderByPriceAsc(Pageable page, String name);
    Page<Product> findAllByNameContainingOrderByDescriptionAsc(Pageable page, String name);

    @Query("SELECT p FROM Product p JOIN p.category c WHERE p.name LIKE %:name% ORDER BY c.name ASC")
    Page<Product> findAllByNameContainingOrderByCategoryNameAsc(Pageable page, String name);


//
    @Query(value = "SELECT * FROM product ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<Product> findRandomProducts(@Param("count") Integer count);
}
