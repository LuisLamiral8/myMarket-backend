package com.myMarket.myMarket.service.impl;

import com.myMarket.myMarket.entity.Category;
import com.myMarket.myMarket.repository.CategoryRepository;
import com.myMarket.myMarket.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category save(Category req) throws Exception {
        Optional<Category> found = categoryRepository.findAllByName(req.getName());
        if (found.isPresent()) {
            throw new Exception("The category exists");
        } else {
            return categoryRepository.save(req);
        }
    }

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAllByOrderByNameAsc();
    }

    @Override
    public boolean delete(Long id) throws Exception {
        Optional<Category> found = categoryRepository.findById(id);
        if (found.isPresent()) {
            categoryRepository.deleteById(id);
            return true;
        } else {
            throw new Exception("The category doesn't exists");
        }
    }

    @Override
    public Category edit(Category req) throws Exception {
        if (req.getId() == null) {
            throw new Exception("The id can't be null");
        }
        Optional<Category> found = categoryRepository.findById(req.getId());
        if (found.isPresent()) {
            return categoryRepository.save(req);
        } else {
            throw new Exception("The category doesn't exists");
        }
    }
}
