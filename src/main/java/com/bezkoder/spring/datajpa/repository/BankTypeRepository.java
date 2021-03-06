package com.bezkoder.spring.datajpa.repository;
import com.bezkoder.spring.datajpa.model.Bank_type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BankTypeRepository extends JpaRepository<Bank_type, Long> {

}