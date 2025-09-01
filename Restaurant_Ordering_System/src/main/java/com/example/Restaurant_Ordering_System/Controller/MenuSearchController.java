package com.example.Restaurant_Ordering_System.Controller;

import com.example.Restaurant_Ordering_System.Entity.MenuCategory;
import com.example.Restaurant_Ordering_System.Entity.MenuItem;
import com.example.Restaurant_Ordering_System.Service.MenuService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuSearchController {

    private final MenuService menuService;
    public MenuSearchController(MenuService menuService) { this.menuService = menuService; }

    @GetMapping("/search")
    public List<MenuItem> search(
            @RequestParam(required = false) MenuCategory category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean vegetarian,
            @RequestParam(required = false) Boolean vegan,
            @RequestParam(required = false) Boolean glutenFree,
            @RequestParam(required = false) Boolean available
    ) {
        return menuService.search(category, minPrice, maxPrice, vegetarian, vegan, glutenFree, available);
    }
}
