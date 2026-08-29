package com.example.transactionstarter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(@Valid @RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @GetMapping("/customer/{customerId}")
    public List<Transaction> getTransactionsByCustomer(
            @PathVariable String customerId) {
        return transactionService.getTransactionsByCustomer(customerId);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransaction(
            @PathVariable String transactionId) {
        return transactionService.getTransaction(transactionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{transactionId}/status")
    public ResponseEntity<Transaction> updateTransactionStatus(
            @PathVariable String transactionId,
            @RequestParam String status) {
        return transactionService
                .updateTransactionStatus(transactionId, status)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}