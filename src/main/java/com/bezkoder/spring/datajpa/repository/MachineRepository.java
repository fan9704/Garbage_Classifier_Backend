package com.bezkoder.spring.datajpa.repository;
import com.bezkoder.spring.datajpa.model.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {

}