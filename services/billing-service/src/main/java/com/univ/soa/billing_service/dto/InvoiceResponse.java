package com.univ.soa.billing_service.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceResponse {
    private Long id;
    private Long studentId;
    private Double amount;
    private LocalDate createdAt;
    private String status;
    private List<Item> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private String description;
        private Double price;
        private Integer quantity;
    }
}
