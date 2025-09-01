package com.example.Restaurant_Ordering_System.DTO;

import com.example.Restaurant_Ordering_System.Entity.PaymentMethod;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class OrderDtos {
    public record OrderItemRequest(@NotNull Long menuItemId, int quantity) {}
    public record OrderRequest(
            Long customerId,
            PaymentMethod paymentMethod,
            String paymentRef,
            List<OrderItemRequest> items
    ) {}

    public record OrderItemResponse(
            Long id,
            Long menuItemId,
            String name,
            int quantity,
            BigDecimal unitPrice,
            BigDecimal subtotal
    ) {}

    public record OrderResponse(
            Long id,
            String status,
            boolean paid,
            PaymentMethod paymentMethod,
            String paymentRef,
            List<OrderItemResponse> items
    ) {}
    public record StatusUpdateRequest(String status) {}
}
