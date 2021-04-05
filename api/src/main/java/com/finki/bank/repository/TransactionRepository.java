package com.finki.bank.repository;

import com.finki.bank.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    //List<Transaction> getTransactionsByCreatedDateBetweenAndAmountBetweenAndFromAccount_IdOrToAccount_IdOrderByCreatedDateDesc(LocalDate startDate, LocalDate endDate, BigDecimal startAmount, BigDecimal endAmount);

    //List<Transaction> findFirst10getTransactionsByCreatedDateBetweenAndAmountBetweenOrderByCreatedDateDesc(LocalDate startDate, LocalDate endDate, BigDecimal startAmount, BigDecimal endAmount);
}
