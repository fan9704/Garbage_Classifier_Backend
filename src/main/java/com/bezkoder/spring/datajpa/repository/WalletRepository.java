package com.bezkoder.spring.datajpa.repository;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;


@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    @Query(value="select sum(change_value) from wallet where user_id = :userId", nativeQuery=true)
    BigDecimal getCurrentCash(int userId);
    @Query(value = "select * from wallet where user_id = :userId",nativeQuery = true)
    List<Wallet> findAllByUserid(long userId);
}