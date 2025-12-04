package com.univ.soa.billing_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "invoice_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private Double price;

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
}
