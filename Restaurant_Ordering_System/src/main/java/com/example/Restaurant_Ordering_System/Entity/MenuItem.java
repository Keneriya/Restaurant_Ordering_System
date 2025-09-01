package com.example.Restaurant_Ordering_System.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity

public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private boolean active = true;
    private boolean available = true;           // new
    private boolean vegetarian = false;         // new
    private boolean vegan = false;              // new
    private boolean glutenFree = false;         // new

    @Enumerated(EnumType.STRING)
    private MenuCategory category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public MenuCategory getCategory() {
        return null;
    }

    public Boolean isVegetarian() {
        return null;
    }

    public Boolean isVegan() {
        return null;
    }

    public Boolean isGlutenFree() {
        return null;
    }

    public Boolean isAvailable() {
        return null;
    }

}

