package com.example.banking_app.controller;


import com.example.banking_app.entity.Transaction;
import com.example.banking_app.service.TransactionLogService;
import com.example.banking_app.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    public TransactionController (TransactionService transactionService){
        this.transactionService = transactionService;
    }


    @GetMapping("/{accountNumber}")
    public ResponseEntity<?>  getTransactionDetailsById(@PathVariable String accountNumber){
        List<Transaction> response = transactionService.getTransactionDetailsByAccountNumber(accountNumber);
        return ResponseEntity.ok(response);
    }

}
