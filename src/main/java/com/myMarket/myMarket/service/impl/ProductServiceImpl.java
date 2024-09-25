package com.myMarket.myMarket.service.impl;

import com.myMarket.myMarket.dto.GetProductsDTO;
import com.myMarket.myMarket.dto.RegisterProductDTO;
import com.myMarket.myMarket.entity.Category;
import com.myMarket.myMarket.entity.Product;
import com.myMarket.myMarket.entity.User;
import com.myMarket.myMarket.repository.CategoryRepository;
import com.myMarket.myMarket.repository.ProductRepository;
import com.myMarket.myMarket.repository.UserRepository;
import com.myMarket.myMarket.service.CategoryService;
import com.myMarket.myMarket.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Product save(RegisterProductDTO req) throws Exception {
        if (validateCategories(req.getCategory())) {
            throw new Exception("One or more categories do not exist.");
        }

        Product product = Product.builder()
                .name(req.getName())
                .description(req.getDescription())
                .price(req.getPrice())
                .category(req.getCategory())
                .isActive(true)
                .seller(req.getSeller())
                .isSold(false)
                .buyer(null)
                .stock(req.getStock())
                .build();
        return productRepository.save(product);
    }

    @Override
    public GetProductsDTO getAll() {
        List<Product> repoResponse = productRepository.findAll();

        return GetProductsDTO.builder()
                .products(repoResponse)
                .totalProducts(repoResponse.size())
                .build();
    }

    @Override
    public GetProductsDTO getAllByPage(Integer pageNo, Integer itemsPage) {
        Pageable pageable = PageRequest.of(pageNo, itemsPage);
        Page<Product> pageResult = productRepository.findAll(pageable);
        Integer totalProducts = productRepository.findAll().size();
        return GetProductsDTO.builder()
                .products(pageResult.getContent())
                .totalProducts(totalProducts)
                .build();
    }

    @Override
    @Transactional
    public Product edit(Product req) throws Exception {
        Optional<Product> productExists = productRepository.findById(req.getId());
        if (productExists.isEmpty()) {
            throw new Exception("The product doesn't exist");
        } else if (!req.getCategory().isEmpty() && validateCategories(req.getCategory())) {
            throw new Exception("One or more categories do not exist.");
        } else {
            Product oldProduct = productExists.get();
            Product newProduct = Product.builder()
                    .id(req.getId())
                    .name(req.getName() != null && !req.getName().isEmpty() ? req.getName() : oldProduct.getName())
                    .description(req.getDescription() != null && !req.getDescription().isEmpty() ? req.getDescription() : oldProduct.getDescription())
                    .price(req.getPrice() != null ? req.getPrice() : oldProduct.getPrice())
                    .category(req.getCategory() != null && !req.getCategory().isEmpty() ? req.getCategory() : oldProduct.getCategory())
                    .isActive(req.isActive())
                    .seller(oldProduct.getSeller())
                    .isSold(oldProduct.isSold())
                    .buyer(oldProduct.getBuyer())
                    .stock(req.getStock() != null ? req.getStock() : oldProduct.getStock())
                    .build();
            System.out.println("Guardando producto: " + newProduct);

            return productRepository.save(newProduct);
        }
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        Optional<Product> found = productRepository.findById(id);
        if (found.isPresent()) {
            productRepository.deleteById(id);
            return true;
        } else {
            throw new Exception("The product doesn't exists");
        }
    }

    @Override
    public Product findById(Long id) throws Exception {
        Optional<Product> found = productRepository.findById(id);
        if (found.isPresent()) {
            return found.get();
        } else {
            throw new Exception("Product not found");
        }
    }

    @Override
    public GetProductsDTO getMyProducts(Long id) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            List<Product> products = productRepository.findALlBySellerId(id);
            return GetProductsDTO.builder()
                    .products(products)
                    .totalProducts(products.size())
                    .build();
        } else {
            throw new Exception("The user doesn't exists");
        }
    }

    @Override
    public List<Product> getRandomProducts(Integer products) {
        return productRepository.findRandomProducts(products);
    }


    private Boolean validateCategories(List<Category> categories) {
        List<Long> categoryIds = categories.stream()
                .map(Category::getId)
                .collect(Collectors.toList());
        List<Category> existingCategories = categoryRepository.findAllById(categoryIds);

        if (existingCategories.size() != categoryIds.size()) {
            return true;
        }
        {
            return false;
        }
    }
}
