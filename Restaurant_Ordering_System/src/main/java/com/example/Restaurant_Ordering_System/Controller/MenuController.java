package com.example.Restaurant_Ordering_System.Controller;

import com.example.Restaurant_Ordering_System.DTO.MenuDtos;
import com.example.Restaurant_Ordering_System.Service.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // Customer & Admin: Get all menu items
    @GetMapping
    public List<MenuDtos.MenuResponse> getAllMenuItems() {
        return menuService.getAllMenuItems();
    }

    // Admin: Add a new menu item
    @PostMapping
    public ResponseEntity<MenuDtos.MenuResponse> createMenuItem(@RequestBody MenuDtos.MenuRequest request) {
        return ResponseEntity.ok(convertToResponse(menuService.createMenuItem(request)));
    }

    // Admin: Update menu item
    @PutMapping("/{id}")
    public ResponseEntity<MenuDtos.MenuResponse> updateMenuItem(@PathVariable Long id,
                                                                @RequestBody MenuDtos.MenuRequest request) {
        return ResponseEntity.ok(convertToResponse(menuService.updateMenuItem(id, request)));
    }

    // Admin: Delete menu item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
        return ResponseEntity.noContent().build();
    }

    // Helper to convert MenuItem entity to MenuResponse DTO
    private MenuDtos.MenuResponse convertToResponse(com.example.Restaurant_Ordering_System.Entity.MenuItem item) {
        return new MenuDtos.MenuResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getImageUrl()
        );
    }
}
