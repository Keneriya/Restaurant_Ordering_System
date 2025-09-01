package com.example.Restaurant_Ordering_System.Service;

import com.example.Restaurant_Ordering_System.DTO.OrderDtos;
import com.example.Restaurant_Ordering_System.Entity.*;
import com.example.Restaurant_Ordering_System.Repositories.MenuItemRepo;
import com.example.Restaurant_Ordering_System.Repositories.OrderItemRepo;
import com.example.Restaurant_Ordering_System.Repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepo orderItemRepository;
    private final MenuItemRepo menuItemRepository;

    private  final OrderEventsPublisher orderEventsPublisher;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepo orderItemRepository,
                        MenuItemRepo menuItemRepository, OrderEventsPublisher orderEventsPublisher) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderEventsPublisher = orderEventsPublisher;
    }

    // Customer: Place order
    public OrderDtos.OrderResponse placeOrder(User customer, OrderDtos.OrderRequest request) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.PENDING);
        Order savedOrder = orderRepository.save(order); // new variable

        List<OrderItem> items = request.items().stream()
                .map(itemReq -> {
                    MenuItem menuItem = menuItemRepository.findById(itemReq.menuItemId())
                            .orElseThrow(() -> new RuntimeException("MenuItem not found"));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setMenuItem(menuItem);
                    orderItem.setQuantity(itemReq.quantity());
                    orderItem.setUnitPrice(menuItem.getPrice());
                    orderItem.setSubtotal(menuItem.getPrice().multiply(BigDecimal.valueOf(itemReq.quantity())));
                    return orderItemRepository.save(orderItem);
                })
                .collect(Collectors.toList()); // ✅ works in all Java versions


        order.setItems(items);

        // publish order event
        orderEventsPublisher.publishOrderUpdate(savedOrder.getId(), mapToResponse(savedOrder));

        return mapToResponse(order);
    }

    // Customer: Order history
    public List<OrderDtos.OrderResponse> getOrdersByCustomer(User customer) {
        return orderRepository.findByCustomer(customer).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Admin: List all orders
    public List<OrderDtos.OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Admin: Update order status
    public OrderDtos.OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        Order savedOrder = orderRepository.save(order);

        // publish order event
        orderEventsPublisher.publishOrderUpdate(savedOrder.getId(), mapToResponse(savedOrder));

        return mapToResponse(savedOrder);
    }


    private OrderDtos.OrderResponse mapToResponse(Order order) {
        List<OrderDtos.OrderItemResponse> items = order.getItems().stream()
                .map(i -> new OrderDtos.OrderItemResponse(
                        i.getId(), i.getMenuItem().getId(),
                        i.getMenuItem().getName(),
                        i.getQuantity(), i.getUnitPrice(),
                        i.getSubtotal()
                )).toList();
        return new OrderDtos.OrderResponse(order.getId(), order.getStatus().name(), items);
    }
}