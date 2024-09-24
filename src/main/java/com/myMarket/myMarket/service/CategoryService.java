package com.myMarket.myMarket.service;

import com.myMarket.myMarket.entity.Category;

import java.util.List;

public interface CategoryService {
    public Category save(Category req) throws Exception;

    public List<Category> getAll();

    public boolean delete(Long id) throws Exception;

    public Category edit(Category req) throws Exception;
}
