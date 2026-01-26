package com.moneyanalyzer.controller;

import com.moneyanalyzer.dto.transaction.TransactionDto;
import com.moneyanalyzer.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping("/getAllTransactions")
    public ResponseEntity<Page<TransactionDto>> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "0") int size
    ){
        Pageable pageable = PageRequest.of(size,
                size,
                Sort.by("transactionAt").descending());

        Page<TransactionDto> result = transactionService.getAllTransactions(pageable);

        return ResponseEntity.ok(result);
    }
    /*@GetMapping("/getAllTransactions")
    public ResponseEntity<List<TransactionDto>> getAllTransactions(){
        List<TransactionDto> transactionDTOS = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactionDTOS);
    }*/
}
