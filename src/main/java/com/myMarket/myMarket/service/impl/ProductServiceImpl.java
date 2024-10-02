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

        //Guardo imágenes
        List<String> finalImagePaths = saveImages(images, productSaved.getId());

        productSaved.setImagePaths(finalImagePaths);
        return productRepository.save(productSaved);
    }

    @Override
    public ProductImagePaginatedResponseDTO getAllByPage(Integer pageNo, Integer itemsPage, String opt) throws IOException {
        Pageable pageable = PageRequest.of(pageNo, itemsPage);
        ProductImagePaginatedResponseDTO finalObj = new ProductImagePaginatedResponseDTO();
        Page<Product> pageResult;
        List<Product> products;
        switch (opt) {
            case "NAME":
                pageResult = productRepository.findAllByOrderByNameAsc(pageable);
                products = pageResult.getContent();
                //Este es el array final que devuelve, por un lado los productos ya modificados con las imágenes en blob, y por
                //Otro lado, la info de la paginación
                finalObj.setMainObj(getProductImageB64(products));
                finalObj.setPagingInfo(pageResult);
                return finalObj;
            case "PRICE":
                System.out.println("PRICE");
                pageResult = productRepository.findAllByOrderByPriceAsc(pageable);
                products = pageResult.getContent();
                //Este es el array final que devuelve, por un lado los productos ya modificados con las imágenes en blob, y por
                //Otro lado, la info de la paginación
                finalObj.setMainObj(getProductImageB64(products));
                finalObj.setPagingInfo(pageResult);
                return finalObj;
            case "DESCRIPTION":
                System.out.println("DESCRIPTION");
                pageResult = productRepository.findAllByOrderByDescriptionAsc(pageable);
                products = pageResult.getContent();
                //Este es el array final que devuelve, por un lado los productos ya modificados con las imágenes en blob, y por
                //Otro lado, la info de la paginación
                finalObj.setMainObj(getProductImageB64(products));
                finalObj.setPagingInfo(pageResult);
                return finalObj;
            case "CATEGORY":
                System.out.println("CATEGORY");
                pageResult = productRepository.findAllByOrderByCategoryNameAsc(pageable);
                products = pageResult.getContent();
                //Este es el array final que devuelve, por un lado los productos ya modificados con las imágenes en blob, y por
                //Otro lado, la info de la paginación
                finalObj.setMainObj(getProductImageB64(products));
                finalObj.setPagingInfo(pageResult);
                return finalObj;
            default:
                return null;
        }
    }

    @Override
    public ProductImagePaginatedResponseDTO getAllByName(Integer pageNo, Integer itemsPage, String opt, String searchReq) throws IOException {
        Pageable pageable = PageRequest.of(pageNo, itemsPage);
        ProductImagePaginatedResponseDTO finalObj = new ProductImagePaginatedResponseDTO();
        Page<Product> pageResult;
        List<Product> products;
        switch (opt) {
            case "NAME":
                pageResult = productRepository.findAllByNameContainingOrderByNameAsc(pageable, searchReq);
                products = pageResult.getContent();
                //Este es el array final que devuelve, por un lado los productos ya modificados con las imágenes en blob, y por
                //Otro lado, la info de la paginación
                finalObj.setMainObj(getProductImageB64(products));
                finalObj.setPagingInfo(pageResult);
                return finalObj;
            case "PRICE":
                System.out.println("PRICE");
                pageResult = productRepository.findAllByNameContainingOrderByPriceAsc(pageable, searchReq);
                products = pageResult.getContent();
                //Este es el array final que devuelve, por un lado los productos ya modificados con las imágenes en blob, y por
                //Otro lado, la info de la paginación
                finalObj.setMainObj(getProductImageB64(products));
                finalObj.setPagingInfo(pageResult);
                return finalObj;
            case "DESCRIPTION":
                System.out.println("DESCRIPTION");
                pageResult = productRepository.findAllByNameContainingOrderByDescriptionAsc(pageable, searchReq);
                products = pageResult.getContent();
                //Este es el array final que devuelve, por un lado los productos ya modificados con las imágenes en blob, y por
                //Otro lado, la info de la paginación
                finalObj.setMainObj(getProductImageB64(products));
                finalObj.setPagingInfo(pageResult);
                return finalObj;
            case "CATEGORY":
                System.out.println("CATEGORY");
                pageResult = productRepository.findAllByNameContainingOrderByCategoryNameAsc(pageable, searchReq);
                products = pageResult.getContent();
                //Este es el array final que devuelve, por un lado los productos ya modificados con las imágenes en blob, y por
                //Otro lado, la info de la paginación
                finalObj.setMainObj(getProductImageB64(products));
                finalObj.setPagingInfo(pageResult);
                return finalObj;
            default:
                return null;
        }
    }

    @Override
    @Transactional
    public Product edit(Product req, MultipartFile[] images) throws Exception {
        Optional<Product> productExists = productRepository.findById(req.getId());
        if (productExists.isEmpty()) {
            throw new Exception("The product doesn't exist");
        } else if (!req.getCategory().isEmpty() && validateCategories(req.getCategory())) {
            throw new Exception("One or more categories do not exist.");
        } else {
            Product oldProduct = productExists.get();
            deleteImages(oldProduct.getImagePaths());
            List<String> finalImagePaths = saveImages(images, oldProduct.getId());

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
                    .imagePaths(finalImagePaths)
                    .build();

            return productRepository.save(newProduct);
        }
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        Optional<Product> found = productRepository.findById(id);
        if (found.isPresent()) {
            deleteImages(found.get().getImagePaths());
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
                loadedImage = loadImageB64(finalImagePath);
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
    public Page<Product> getMyProducts(String username, Integer pageNo, Integer itemsPage) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            Pageable pageable = PageRequest.of(pageNo, itemsPage);
            return productRepository.findAllBySellerId(pageable, user.get().getId());
        } else {
            throw new Exception("The user doesn't exists");
        }
    }

    @Override
    public List<ProductImageResponseDTO> getRandomProducts(Integer products) throws IOException {
        List<Product> allProducts = productRepository.findRandomProducts(products);
        return getProductImageB64(allProducts);
    }

    @Override
    public List<ImageResponseDTO> getImagesFileById(Long id) throws Exception {
        List<ImageResponseDTO> imagesResponse = new ArrayList<>();
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            throw new Exception("Product not exists");
        }
        for (String imagePath : product.get().getImagePaths()) {
            String projectPath = System.getProperty("user.dir");
            String finalImagePath = projectPath + "/src/main/resources/static" + imagePath;
            Path path = Paths.get(finalImagePath);
            File imageFile = path.toFile();
            if (imageFile.exists()) {
                byte[] imageData = Files.readAllBytes(path);
                String imageContentType = Files.probeContentType(path);
                String imageName = imageFile.getName();
                imagesResponse.add(new ImageResponseDTO(imageName, imageContentType, imageData));
            }
        }
        return imagesResponse;
    }


    private byte[] loadImageB64(String imagePath) throws IOException {
        //asigno la ruta q traigo a un archivo en sí
        Path path = Paths.get(imagePath);
        //devuelvo lo que leo
        return Files.readAllBytes(path);
    }

    private List<ProductImageResponseDTO> getProductImageB64(List<Product> products) throws IOException {
        // Creo el arraylist que va a devolver
        List<ProductImageResponseDTO> resp = new ArrayList<>();
        //Geteo el directorio del proyecto
        String projectPath = System.getProperty("user.dir");
        //Mapeo los productos q traigo por params
        for (Product product : products) {
            //Geteo el imagepath de la primera imágen del producto, si no tiene imágenes asigna null
            String firstImagePath = product.getImagePaths().isEmpty() ? null : product.getImagePaths().get(0);
            //Creo la variable donde va a estar la imagen base sesentaycuatroeada
            byte[] image64 = null;

            if (firstImagePath != null) {
                //Genero la ruta final la imagen, q contiene el directorio del proyecto +
                // la ruta estática + la de la imágen que asigné más arriba
                String finalImagePath = projectPath + "/src/main/resources/static" + firstImagePath;
                //procedo a pasarlo a B64
                image64 = loadImageB64(finalImagePath);
            }
            resp.add(new ProductImageResponseDTO(product, image64));
        }
        return resp;
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

    private List<String> saveImages(MultipartFile[] images, Long productId) throws IOException {
        List<String> finalImagePaths = new ArrayList<>();
        String projectPath = System.getProperty("user.dir");
        String productDir = projectPath + "/src/main/resources/static/images/products/" + productId;
        File dir = new File(productDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        for (MultipartFile image : images) {
            String fileName = image.getOriginalFilename();
            String finalImagePath = productDir + "/" + fileName;

            // Guardar la imagen
            Files.copy(image.getInputStream(), Paths.get(finalImagePath), StandardCopyOption.REPLACE_EXISTING);

            // Agregar la ruta a la lista
            finalImagePaths.add("/images/products/" + productId + "/" + fileName);
        }
        return finalImagePaths;
    }

    private void deleteImages(List<String> imagePaths) throws Exception {
        String projectPath = System.getProperty("user.dir");
        for (String imagePath : imagePaths) {
            String finalImagePath = projectPath + "/src/main/resources/static" + imagePath;
            File imageFile = new File(finalImagePath);
            if (imageFile.exists()) {
                if (!imageFile.delete()) {
                    throw new Exception("Failed to delete image: " + finalImagePath);
                }
            }
        }
    }
}
