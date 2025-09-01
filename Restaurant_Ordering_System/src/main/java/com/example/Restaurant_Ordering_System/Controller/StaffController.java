package com.example.Restaurant_Ordering_System.Controller;

import com.example.Restaurant_Ordering_System.DTO.OrderDtos;
import com.example.Restaurant_Ordering_System.Entity.OrderStatus;
import com.example.Restaurant_Ordering_System.Service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final OrderService orderService;
    public StaffController(OrderService orderService) { this.orderService = orderService; }

    // CHEF: get pending orders
    @GetMapping("/orders/pending")
    public List<OrderDtos.OrderResponse> pending(){
        return orderService.getByStatus(OrderStatus.PENDING);
    }

    // CHEF: mark cooking
    @PatchMapping("/orders/{id}/cook")
    public OrderDtos.OrderResponse markCooking(@PathVariable Long id){
        return orderService.updateOrderStatus(id, OrderStatus.COOKING);
    }

    // DELIVERY: mark out for delivery
    @PatchMapping("/orders/{id}/out")
    public OrderDtos.OrderResponse markOutForDelivery(@PathVariable Long id){
        return orderService.updateOrderStatus(id, OrderStatus.OUT_FOR_DELIVERY);
    }

    // DELIVERY: complete
    @PatchMapping("/orders/{id}/complete")
    public OrderDtos.OrderResponse complete(@PathVariable Long id){
        return orderService.updateOrderStatus(id, OrderStatus.COMPLETED);
    }
}
