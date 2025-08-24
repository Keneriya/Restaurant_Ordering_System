package com.example.Restaurant_Ordering_System.DTO;

import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class OrderDtos {
    public record OrderItemRequest(@NotNull Long menuItemId, int quantity) {}
    public record OrderRequest(List<OrderItemRequest> items) {}

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
            List<OrderItemResponse> items
    ) {}
}
