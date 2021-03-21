package com.finki.bank.repository;

import com.finki.bank.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

//    @Query(value = "select distinct users from User users left join fetch users.favouriteUsers",
//            countQuery = "select count(distinct users) from User users")
//    Page<User> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct users from User users left join fetch users.favouriteUsers")
    List<User> findAllWithEagerRelationships();

    @Query("select users from User users left join fetch users.favouriteUsers where users.id =:id")
    Optional<User> findOneWithEagerRelationships(@Param("id") Long id);

    Optional<User> findOneByEmailIgnoreCase(String email);

    List<User> findAllByEmailStartsWithIgnoreCase(String search);


}
