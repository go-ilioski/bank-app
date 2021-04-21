package com.finki.bank.repository;

import com.finki.bank.domain.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {

    //List<Transaction> getTransactionsByCreatedDateBetweenAndAmountBetweenAndFromAccount_IdOrToAccount_IdOrderByCreatedDateDesc(LocalDate startDate, LocalDate endDate, BigDecimal startAmount, BigDecimal endAmount);

    //List<Transaction> findFirst10getTransactionsByCreatedDateBetweenAndAmountBetweenOrderByCreatedDateDesc(LocalDate startDate, LocalDate endDate, BigDecimal startAmount, BigDecimal endAmount);

    @Query("select transactions from Transaction transactions " +
            "where (transactions.toAccount.id =:id or transactions.fromAccount.id =:id) " +
            "and transactions.createdDate between :startDate and :endDate " +
            "order by transactions.createdDate desc")
    List<Transaction> findTransactionByCreatedDate(@Param("id") Long id, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);


    @Query("select transactions from Transaction transactions " +
            "where (transactions.toAccount.id =:id or transactions.fromAccount.id =:id) " +
            "and transactions.createdDate between :startDate and :endDate " +
            "order by transactions.createdDate desc")
    Page<Transaction> findTransactionsPageable(Pageable pageable, @Param("id") Long id, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

}
