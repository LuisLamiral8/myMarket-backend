package com.myMarket.myMarket.repository;

import com.myMarket.myMarket.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Optional<Category> findAllByName(String name);

    public List<Category> findAllByOrderByNameAsc();
}
