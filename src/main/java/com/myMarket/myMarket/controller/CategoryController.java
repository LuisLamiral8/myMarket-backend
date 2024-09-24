package com.myMarket.myMarket.controller;

import com.myMarket.myMarket.entity.Category;
import com.myMarket.myMarket.entity.Product;
import com.myMarket.myMarket.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping(value = "save")
    public ResponseEntity<Object> save(@RequestBody Category req) {
        try {
            Category response = categoryService.save(req);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @GetMapping(value = "getAll")
    public ResponseEntity<Object> getAll() {
        try {
            List<Category> response = categoryService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @PostMapping(value = "edit")
    public ResponseEntity<Object> edit(@RequestBody Category req) {
        try {
            Category response = categoryService.edit(req);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @PostMapping(value = "deleteById")
    public ResponseEntity<Object> deleteById(@RequestParam Long id) {
        try {
            Boolean response = categoryService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }
}

