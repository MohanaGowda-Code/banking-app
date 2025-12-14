package com.example.banking_app.controller;

import com.example.banking_app.dto.DepositRequest;
import com.example.banking_app.dto.TransferRequest;
import com.example.banking_app.service.BankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;


@RestController
@RequestMapping("/api/bank")
public class BankingController {

    private final BankingService bankingService;

    public BankingController(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody DepositRequest depositRequest) {
        BigDecimal balance = bankingService.deposit(depositRequest.getAccount(), depositRequest.getAmount());
        return ResponseEntity.ok(Map.of(
                "message", "Deposit successful",
                "account", depositRequest.getAccount(),
                "balance", balance
        ));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody DepositRequest depositRequest) {

        BigDecimal balance = bankingService.withdraw(depositRequest.getAccount(), depositRequest.getAmount());
        return ResponseEntity.ok(Map.of(
                "message", "Withdrawal successful",
                "account", depositRequest.getAccount(),
                "balance", balance
        ));

    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest transferRequest) {

            BigDecimal balance = bankingService.transfer(transferRequest.getFrom(), transferRequest.getTo(), transferRequest.getAmount());
            return ResponseEntity.ok(Map.of(
                    "message","Transfer successful",
                        "From account", transferRequest.getFrom(),
                    "to account", transferRequest.getTo(),
                    "balance", balance
            ));

    }
    @PostMapping("/check-balance")
    public ResponseEntity<?> checkBalance(@RequestBody DepositRequest depositRequest){
        BigDecimal balance = bankingService.checkBalance(depositRequest.getAccount());

        return  ResponseEntity.ok(Map.of(
                "message", "Data successfully",
                "balance", balance
        ));
    }



}
