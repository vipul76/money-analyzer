package com.moneyanalyzer.service;
import com.moneyanalyzer.dto.transaction.TransactionDto;
import com.moneyanalyzer.entity.*;
import com.moneyanalyzer.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public TransactionService(TransactionRepository transactionRepository, UserService userService, CategoryService categoryService){
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Transactional
    public void saveTransactions(List<TransactionDto> transactions){
        if(transactions == null || transactions.isEmpty()) return;

        List<TransactionEntity> entities = transactions.stream()
                .map(this::toEntity)
                .toList();

        transactionRepository.saveAll(entities);
    }

    public Page<TransactionDto> getAllTransactions(Pageable page) {
        Optional<User> user = userService.getCurrentUser();
        if(user.isPresent()) {
            Page<TransactionEntity> transactions = transactionRepository.findByUser(user.get(), page);
            return transactions.map(trans -> {
                TransactionDto dto = new TransactionDto();
                dto.setDate(LocalDate.from(trans.getTransactionAt()));
                dto.setType(String.valueOf(trans.getType()));
                dto.setAmount(trans.getAmount());
                dto.setDescription(trans.getDescription());
                return dto;
            });
        }
        else
            return null;
    }

    private TransactionEntity toEntity(TransactionDto transactionDto){
        TransactionEntity transactionEntity = new TransactionEntity();

        Optional<User> currentUser = userService.getCurrentUser();
        if(currentUser.isPresent()) {
            Account account = currentUser.get().getAccounts().get(0);
            List<Category> category = categoryService.findByCategory(currentUser.get());
            transactionEntity.setType(TransactionType.valueOf(transactionDto.getType()));
            transactionEntity.setAmount(transactionDto.getAmount());
            transactionEntity.setDescription(transactionDto.getDescription());
            transactionEntity.setTransactionAt(transactionDto.getDate().atStartOfDay());
            transactionEntity.setCreatedAt(LocalDateTime.now());
            transactionEntity.setAccount(account);
            transactionEntity.setCategory(category.get(1));
            transactionEntity.setUser(currentUser.get());
        }
        return transactionEntity;
    }
}
/*
public List<TransactionDto> getAllTransactions() {
        //List<TransactionDto> transactionDtos = new ArrayList<>();
        List<TransactionEntity> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transaction -> {
                    TransactionDto transactionDto = new TransactionDto();
                    transactionDto.setDate(LocalDate.from(transaction.getTransactionAt()));
                    transactionDto.setDescription(transaction.getDescription());
                    transactionDto.setAmount(transaction.getAmount());
                    transactionDto.setType(String.valueOf(transaction.getType()));
                    return transactionDto;
                }).toList();
    }
 */