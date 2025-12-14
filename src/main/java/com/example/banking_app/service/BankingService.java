package com.example.banking_app.service;


import com.example.banking_app.entity.Account;
import com.example.banking_app.entity.Transaction;
import com.example.banking_app.exception.AccountNotFoundException;
import com.example.banking_app.exception.InsufficientFundsException;
import com.example.banking_app.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BankingService {

    private final AccountRepository accountRepository;
    private final TransactionLogService transactionLogService;

    public BankingService(AccountRepository accountRepository, TransactionLogService transactionLogService) {
        this.accountRepository = accountRepository;
        this.transactionLogService = transactionLogService;
    }

    @Transactional(isolation= Isolation.REPEATABLE_READ)
    public BigDecimal deposit(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumberForUpdate(accountNumber).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        transactionLogService.saveLog(new Transaction(null, accountNumber, amount, Transaction.TransactionType.DEPOSIT));

        return account.getBalance();
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public BigDecimal withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() -> new RuntimeException("Account not found"));
        try {
            if (account.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds");
            }
            account.setBalance(account.getBalance().subtract(amount));
            accountRepository.save(account);
            transactionLogService.saveLog(new Transaction(accountNumber, null, amount, Transaction.TransactionType.WITHDRAWAL));

            return account.getBalance();
        } catch (RuntimeException e) {
            transactionLogService.saveLog(new Transaction(accountNumber, null, amount, Transaction.TransactionType.WITHDRAWAL));
            throw e;
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public BigDecimal transfer(String fromAcc, String toAcc, BigDecimal amount) {
        Account sender = accountRepository.findByAccountNumber(fromAcc).orElseThrow(() -> new RuntimeException("Sender not found"));
        Account receiver = accountRepository.findByAccountNumber(toAcc).orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));
        accountRepository.save(sender);
        accountRepository.save(receiver);

        transactionLogService.saveLog(new Transaction(fromAcc, toAcc, amount, Transaction.TransactionType.TRANSFER));

        return sender.getBalance();

    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, readOnly = true)
    public BigDecimal checkBalance(String accountNumber){
        Account account= accountRepository.findByAccountNumber(accountNumber).orElseThrow(()->new RuntimeException("Account not found"));
       return account.getBalance();
    }


}
