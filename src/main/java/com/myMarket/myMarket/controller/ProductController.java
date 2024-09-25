package com.myMarket.myMarket.controller;

import com.myMarket.myMarket.dto.GetProductsDTO;
import com.myMarket.myMarket.dto.RegisterProductDTO;
import com.myMarket.myMarket.entity.Product;
import com.myMarket.myMarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;


    @PostMapping(value = "save")
    public ResponseEntity<Object> saveProduct(@RequestBody RegisterProductDTO req) {
        try {
            Product response = productService.save(req);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @GetMapping(value = "getAll")
    public ResponseEntity<Object> getAll() {
        try {
            GetProductsDTO response = productService.getAll();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @GetMapping(value = "getAllByPage")
    public ResponseEntity<Object> getAllByPage(@RequestParam Integer pageNo, @RequestParam Integer itemsPage) {
        try {
            GetProductsDTO response = productService.getAllByPage(pageNo, itemsPage);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @PostMapping(value = "edit")
    public ResponseEntity<Object> edit(@RequestBody Product req) {
        try {
            Product response = productService.edit(req);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @PostMapping(value = "deleteById")
    public ResponseEntity<Object> deleteById(@RequestParam Long id) {
        try {
            Boolean response = productService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @GetMapping(value = "findById")
    public ResponseEntity<Object> findById(@RequestParam Long id) {
        try {
            Product response = productService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @GetMapping(value="findAllByUser")
    public ResponseEntity<Object> findAllByUser(@RequestParam Long id) {
        try {
            GetProductsDTO response = productService.getMyProducts(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @GetMapping(value="getRandomProducts")
    public ResponseEntity<Object> getRandomProducts(@RequestParam Integer products) {
        try {
            List<Product> response = productService.getRandomProducts(products);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }
}
