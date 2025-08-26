package com.example.Restaurant_Ordering_System.Service;

import com.example.Restaurant_Ordering_System.DTO.MenuDtos;
import com.example.Restaurant_Ordering_System.Entity.MenuItem;
import com.example.Restaurant_Ordering_System.Repositories.MenuItemRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuItemRepo menuItemRepository;

    public MenuService(MenuItemRepo menuItemRepository) {
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuDtos.MenuResponse> getAllMenuItems() {
        return menuItemRepository.findByActiveTrue().stream()
                .map(i -> new MenuDtos.MenuResponse(i.getId(), i.getName(), i.getDescription(),
                        i.getPrice(), i.getImageUrl()))
                .collect(Collectors.toList());
    }

    public MenuItem createMenuItem(MenuDtos.MenuRequest request) {
        MenuItem item = new MenuItem();
        item.setName(request.name());
        item.setDescription(request.description());
        item.setPrice(request.price());
        item.setImageUrl(request.imageUrl());
        item.setActive(request.active());
        return menuItemRepository.save(item);
    }

    public MenuItem updateMenuItem(Long id, MenuDtos.MenuRequest request) {
        MenuItem item = menuItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MenuItem not found"));
        item.setName(request.name());
        item.setDescription(request.description());
        item.setPrice(request.price());
        item.setImageUrl(request.imageUrl());
        item.setActive(request.active());
        return menuItemRepository.save(item);
    }

    public void deleteMenuItem(Long id) {
        menuItemRepository.deleteById(id);
    }



}