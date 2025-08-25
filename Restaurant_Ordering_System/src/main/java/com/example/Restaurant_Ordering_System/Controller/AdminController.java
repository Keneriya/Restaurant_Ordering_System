package com.example.Restaurant_Ordering_System.Controller;

import com.example.Restaurant_Ordering_System.DTO.MenuDtos;
import com.example.Restaurant_Ordering_System.DTO.OrderDtos;
import com.example.Restaurant_Ordering_System.Entity.OrderStatus;
import com.example.Restaurant_Ordering_System.Service.MenuService;
import com.example.Restaurant_Ordering_System.Service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final MenuService menuService;
    private final OrderService orderService;

    public AdminController(MenuService menuService, OrderService orderService) {
        this.menuService = menuService;
        this.orderService = orderService;
    }

    // Menu CRUD
    @PostMapping("/menu")
    public MenuDtos.MenuResponse createMenu(@RequestBody MenuDtos.MenuRequest req) {
        return new MenuDtos.MenuResponse(
                menuService.createMenuItem(req).getId(),
                req.name(), req.description(), req.price(), req.imageUrl()
        );
    }

    @PutMapping("/menu/{id}")
    public MenuDtos.MenuResponse updateMenu(@PathVariable Long id, @RequestBody MenuDtos.MenuRequest req) {
        return new MenuDtos.MenuResponse(
                menuService.updateMenuItem(id, req).getId(),
                req.name(), req.description(), req.price(), req.imageUrl()
        );
    }

    @DeleteMapping("/menu/{id}")
    public String deleteMenu(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
        return "Deleted menu item " + id;
    }

    // Orders
    @GetMapping("/orders")
    public List<OrderDtos.OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PatchMapping("/orders/{id}/status")
    public OrderDtos.OrderResponse updateOrderStatus(@PathVariable Long id, @RequestBody String status) {
        return orderService.updateOrderStatus(id, OrderStatus.valueOf(status.toUpperCase()));
    }
}