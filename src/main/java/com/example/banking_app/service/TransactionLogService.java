package com.example.banking_app.service;

import com.example.banking_app.entity.Transaction;
import com.example.banking_app.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionLogService {
    private  final TransactionRepository transactionRepository;

    public TransactionLogService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(Transaction transaction) {
        transactionRepository.save(transaction);
    }

}
