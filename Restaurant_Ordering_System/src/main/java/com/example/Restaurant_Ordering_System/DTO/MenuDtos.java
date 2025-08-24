package com.example.Restaurant_Ordering_System.DTO;

import java.math.BigDecimal;

public class MenuDtos {
    public record MenuResponse(
            Long id,
            String name,
            String description,
            BigDecimal price,
            String imageUrl
    ) {}

    public record MenuRequest(
            String name,
            String description,
            BigDecimal price,
            String imageUrl,
            Boolean active
    ) {}
}
