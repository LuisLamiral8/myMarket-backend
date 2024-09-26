package com.myMarket.myMarket.service.impl;

import com.myMarket.myMarket.dto.*;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
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
    public Product save(RegisterProductDTO req, MultipartFile[] images) throws Exception {
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

        Product productSaved = productRepository.save(product);

        String projectPath = System.getProperty("user.dir");

        String productDir = projectPath + "/src/main/resources/static/images/products/" + productSaved.getId();

        File dir = new File(productDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        List<String> finalImagePaths = new ArrayList<>();
        for (MultipartFile image : images) {
            String fileName = image.getOriginalFilename();
            String finalImagePath = productDir + "/" + fileName;

            Files.copy(image.getInputStream(), Paths.get(finalImagePath), StandardCopyOption.REPLACE_EXISTING);


            finalImagePaths.add("/images/products/" + productSaved.getId() + "/" + fileName);
        }
        productSaved.setImagePaths(finalImagePaths);
        return productRepository.save(productSaved);
    }

    @Override
    public ProductImagePaginatedResponseDTO getAllByPage(Integer pageNo, Integer itemsPage) throws IOException {
        //Seteo las variables para hacer el paginado, y hago la consulta de la página
        Pageable pageable = PageRequest.of(pageNo, itemsPage);
        Page<Product> pageResult = productRepository.findAll(pageable);
        List<Product> products = pageResult.getContent();
        //Creo el ArrayList para modificar el objeto de la consulta anterior, para devolver directamente el blob en la imagen
        List<ProductImageResponseDTO> productResponseList = new ArrayList<>();
        //Getteo el directorio del proyecto
        String projectPath = System.getProperty("user.dir");
        for (Product product : products) {
            //Getteo el imagePath del producto
            String imagePath = product.getImagePaths().isEmpty() ? null : product.getImagePaths().get(0);
            byte[] imageBlob = null;
            //Genero la ruta final de la imagen, osea donde está guardada
            String finalImagePath = projectPath + "/src/main/resources/static" + imagePath;

            if (imagePath != null) {
                //Lo convierto en blob
                imageBlob = loadImageBlob(finalImagePath);
            }
            //Lo meto en el arraylist de los productos con las imagenes ya blobeadas
            productResponseList.add(new ProductImageResponseDTO(product, imageBlob));
        }
        //Este es el array final que devuelve, por un lado los productos ya modificados con las imágenes en blob, y por
        //Otro lado, la info de la paginación
        ProductImagePaginatedResponseDTO finalObj = new ProductImagePaginatedResponseDTO();
        finalObj.setMainObj(productResponseList);
        finalObj.setPagingInfo(pageResult);
        return finalObj;
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
    public ProductImagesResponseDTO findById(Long id) throws Exception {
        Optional<Product> found = productRepository.findById(id);
        if (found.isPresent()) {

            Product product = found.get();
            ProductImagesResponseDTO response = new ProductImagesResponseDTO();
            List<byte[]> images = new ArrayList<>();
            for (String imagePath : product.getImagePaths()) {
                String projectPath = System.getProperty("user.dir");
                byte[] loadedImage = null;
                String finalImagePath = projectPath + "/src/main/resources/static" + imagePath;
                loadedImage = loadImageBlob(finalImagePath);
                images.add(loadedImage);
            }
            response.setProduct(product);
            response.setImages(images);
            return response;


        } else {
            throw new Exception("Product not found");
        }
    }


    @Override
    public Page<Product> getMyProducts(Long id, Integer pageNo, Integer itemsPage) throws Exception {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            Pageable pageable = PageRequest.of(pageNo, itemsPage);
            Page<Product> products = productRepository.findAllBySellerId(pageable, id);
            return products;
        } else {
            throw new Exception("The user doesn't exists");
        }
    }

    @Override
    public List<ProductImageResponseDTO> getRandomProducts(Integer products) throws IOException {
        List<Product> allProducts = productRepository.findRandomProducts(products);
        List<ProductImageResponseDTO> productResponseList = new ArrayList<>();
        String projectPath = System.getProperty("user.dir");
        for (Product product : allProducts) {
            String firstImagePath = product.getImagePaths().isEmpty() ? null : product.getImagePaths().get(0);
            byte[] imageBlob = null;
            String finalImagePath = projectPath + "/src/main/resources/static" + firstImagePath;

            if (firstImagePath != null) {
                imageBlob = loadImageBlob(finalImagePath);
            }
            productResponseList.add(new ProductImageResponseDTO(product, imageBlob));
        }
        return productResponseList;

    }

    public byte[] loadImageBlob(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        return Files.readAllBytes(path);
    }

    private List<String> saveImages(MultipartFile[] images) throws IOException {
        List<String> imagePaths = new ArrayList<>();
        for (MultipartFile image : images) {
            String imagePath = "/images/" + image.getOriginalFilename();
            Files.copy(image.getInputStream(), Paths.get(imagePath), StandardCopyOption.REPLACE_EXISTING);
            imagePaths.add(imagePath);
        }
        return imagePaths;
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
