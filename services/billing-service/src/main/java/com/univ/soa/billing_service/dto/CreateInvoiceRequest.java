package com.univ.soa.billing_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateInvoiceRequest {

    @NotNull
    private Long studentId;

    @NotNull
    private List<Item> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        @NotNull
        private String description;
        @NotNull
        private Double price;
        @NotNull
        private Integer quantity;
    }
}
