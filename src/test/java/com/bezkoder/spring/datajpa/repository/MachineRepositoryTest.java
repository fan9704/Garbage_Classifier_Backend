package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.configuration.DaoConfig;
import com.bezkoder.spring.datajpa.model.Machine;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.model.Wallet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.HashSet;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = DaoConfig.class)
public class MachineRepositoryTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private MachineRepository machineRepository;
    @Test
    void findAllMachineByLocation() {
        User u1 =new User(1,"user1","user1@gmail.com","user1","user1","user1",false,"000", new HashSet<>(),null);
        Wallet w1 = new Wallet(1,new BigDecimal(0),"Create Account",u1,null);

        Machine machine =new Machine(1,"Hsinchu",true,false,null,u1,null,null);

        Machine ResponseMachine = machineRepository.save(machine);

        Assertions.assertNotNull(ResponseMachine);
        Assertions.assertNotNull(ResponseMachine.getId());
        System.out.println("Test findAllMachineByLocation Pass");
    }
}
