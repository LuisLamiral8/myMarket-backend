package com.myMarket.myMarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    String description;
    Float price;
    @ManyToMany
    @JoinTable(
            name = "product_category", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    List<Category> category;
    boolean isActive;
    @ManyToOne
    User seller;
    boolean isSold;
    @ManyToOne
    User buyer;
    Integer stock;
    @ElementCollection
    private List<String> imagePaths;

}
