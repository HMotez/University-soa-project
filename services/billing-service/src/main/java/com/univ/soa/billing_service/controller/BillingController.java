package com.univ.soa.billing_service.controller;

import com.univ.soa.billing.dto.CreateInvoiceRequest;
import com.univ.soa.billing_service.dto.InvoiceResponse;
import com.univ.soa.billing_service.service.BillingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping("/invoices")
    public ResponseEntity<InvoiceResponse> createInvoice(@Valid @RequestBody CreateInvoiceRequest request) {
        InvoiceResponse response = billingService.createInvoice(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/invoices")
    public ResponseEntity<List<InvoiceResponse>> getAll() {
        return ResponseEntity.ok(billingService.getAll());
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<InvoiceResponse> getById(@PathVariable Long id) {
        InvoiceResponse resp = billingService.getById(id);
        if (resp == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/invoices/student/{studentId}")
    public ResponseEntity<List<InvoiceResponse>> getByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(billingService.getByStudentId(studentId));
    }

    @PostMapping("/invoices/{id}/pay")
    public ResponseEntity<InvoiceResponse> pay(@PathVariable Long id) {
        InvoiceResponse resp = billingService.payInvoice(id);
        return ResponseEntity.ok(resp);
    }
}
