package com.univ.soa.billing_service.service;

import com.univ.soa.billing_service.dto.CreateInvoiceRequest;
import com.univ.soa.billing_service.dto.InvoiceResponse;
import com.univ.soa.billing_service.entity.Invoice;
import com.univ.soa.billing_service.entity.InvoiceItem;
import com.univ.soa.billing_service.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillingService {

    private final InvoiceRepository invoiceRepository;

    public BillingService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional
    public InvoiceResponse createInvoice(CreateInvoiceRequest request) {
        List<InvoiceItem> items = request.getItems().stream()
                .map(i -> InvoiceItem.builder()
                        .description(i.getDescription())
                        .price(i.getPrice())
                        .quantity(i.getQuantity())
                        .build())
                .collect(Collectors.toList());

        double total = items.stream()
                .mapToDouble(it -> it.getPrice() * it.getQuantity())
                .sum();

        Invoice invoice = Invoice.builder()
                .studentId(request.getStudentId())
                .amount(total)
                .createdAt(LocalDate.now())
                .status("PENDING")
                .items(items)
                .build();

        // set invoice ref in items
        items.forEach(it -> it.setInvoice(invoice));

        Invoice saved = invoiceRepository.save(invoice);
        return mapToResponse(saved);
    }

    public List<InvoiceResponse> getAll() {
        return invoiceRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public InvoiceResponse getById(Long id) {
        return invoiceRepository.findById(id).map(this::mapToResponse).orElse(null);
    }

    public List<InvoiceResponse> getByStudentId(Long studentId) {
        return invoiceRepository.findByStudentId(studentId).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public InvoiceResponse payInvoice(Long id) {
        Invoice inv = invoiceRepository.findById(id).orElseThrow(() -> new RuntimeException("Invoice not found"));
        inv.setStatus("PAID");
        Invoice saved = invoiceRepository.save(inv);
        return mapToResponse(saved);
    }

    private InvoiceResponse mapToResponse(Invoice inv) {
        List<InvoiceResponse.Item> items = inv.getItems().stream()
                .map(it -> new InvoiceResponse.Item(it.getDescription(), it.getPrice(), it.getQuantity()))
                .collect(Collectors.toList());

        return InvoiceResponse.builder()
                .id(inv.getId())
                .studentId(inv.getStudentId())
                .amount(inv.getAmount())
                .createdAt(inv.getCreatedAt())
                .status(inv.getStatus())
                .items(items)
                .build();
    }
}
