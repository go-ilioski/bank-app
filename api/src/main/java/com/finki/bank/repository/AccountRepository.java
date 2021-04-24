package com.finki.bank.repository;

import com.finki.bank.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

//    @Query("SELECT acc from Account acc " +
//            "where acc.owner.role := ")
//    List<Account> findAccountsByOwner_Role_Merchant();
}
