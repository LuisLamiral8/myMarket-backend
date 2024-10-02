package com.myMarket.myMarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myMarket.myMarket.dto.*;
import com.myMarket.myMarket.entity.Product;
import com.myMarket.myMarket.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;


    @PostMapping(value = "save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> saveProduct(
            @RequestPart("product") String req,
            @RequestPart("images") MultipartFile[] images
    ) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RegisterProductDTO product = objectMapper.readValue(req, RegisterProductDTO.class);
            Product response = productService.save(product, images);

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }


    @PostMapping(value = "edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> edit(
            @RequestPart("product") String req,
            @RequestPart("images") MultipartFile[] images
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Product product = objectMapper.readValue(req, Product.class);
            Product response = productService.edit(product, images);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @GetMapping(value = "getAllByPage")
    public ResponseEntity<Object> getAllByPage(@RequestParam Integer pageNo, @RequestParam Integer itemsPage, @RequestParam(defaultValue = "NAME") String opt) {
        try {
            ProductImagePaginatedResponseDTO response = productService.getAllByPage(pageNo, itemsPage, opt);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @GetMapping(value = "getAllByName")
    public ResponseEntity<Object> getAllByName(@RequestParam Integer pageNo, @RequestParam Integer itemsPage, @RequestParam(defaultValue = "NAME") String opt, @RequestParam String searchReq) {
        try {
            ProductImagePaginatedResponseDTO response = productService.getAllByName(pageNo, itemsPage, opt, searchReq);
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
            ProductImagesResponseDTO response = productService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @GetMapping(value = "findAllByUser")
    public ResponseEntity<Object> findAllByUser(@RequestParam String username, @RequestParam Integer pageNo, @RequestParam Integer itemsPage) {
        try {
            Page<Product> response = productService.getMyProducts(username, pageNo, itemsPage);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @GetMapping(value = "getRandomProducts")
    public ResponseEntity<Object> getRandomProducts(@RequestParam Integer products) {
        try {
            List<ProductImageResponseDTO> response = productService.getRandomProducts(products);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }

    @GetMapping(value = "getImagesFileById")
    public ResponseEntity<Object> getImagesFileById(@RequestParam Long id) {
        try {
            List<ImageResponseDTO> response = productService.getImagesFileById(id);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getMessage());
        }
    }
}
