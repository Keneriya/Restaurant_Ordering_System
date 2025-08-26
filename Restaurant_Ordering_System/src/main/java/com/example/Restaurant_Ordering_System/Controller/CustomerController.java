package com.example.Restaurant_Ordering_System.Controller;

import com.example.Restaurant_Ordering_System.DTO.MenuDtos;
import com.example.Restaurant_Ordering_System.DTO.OrderDtos;
import com.example.Restaurant_Ordering_System.Entity.User;
import com.example.Restaurant_Ordering_System.Service.MenuService;
import com.example.Restaurant_Ordering_System.Service.OrderService;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final MenuService menuService;
    private final OrderService orderService;

    public CustomerController(MenuService menuService, OrderService orderService) {
        this.menuService = menuService;
        this.orderService = orderService;
    }

    @GetMapping("/menu")
    public List<MenuDtos.MenuResponse> getMenu() {
        return menuService.getAllMenuItems();
    }

    @PostMapping("/orders")
    public OrderDtos.OrderResponse placeOrder(/*@AuthenticationPrincipal*/ User customer,
                                              @RequestBody OrderDtos.OrderRequest req) {
        return orderService.placeOrder(customer, req);
    }

    @GetMapping("/orders")
    public List<OrderDtos.OrderResponse> getOrders(/*@AuthenticationPrincipal*/ User customer) {
        return orderService.getOrdersByCustomer(customer);
    }
}