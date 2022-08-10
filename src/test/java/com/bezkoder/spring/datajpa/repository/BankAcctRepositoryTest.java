package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.configuration.DaoConfig;
import com.bezkoder.spring.datajpa.model.Bank_acct;
import com.bezkoder.spring.datajpa.model.Bank_type;
import com.bezkoder.spring.datajpa.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import javax.persistence.EntityManager;
import java.util.HashSet;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = DaoConfig.class)
class BankAcctRepositoryTest  {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private BankAcctRepository bankAcctRepository;
    @Test
    void findOneByUser() {
        User user1 =new User(1,"user1","user1@gmail.com","user1","user1","user1",false,"000", new HashSet<>(),null);
        Bank_type bankType1 = new Bank_type(1,"Test1Bank","001");
        Bank_acct bankAcct = new Bank_acct(1,bankType1,"001",user1);

        Bank_acct ResponseBankAcct = bankAcctRepository.save(bankAcct);

        Assertions.assertNotNull(ResponseBankAcct);
        Assertions.assertNotNull(ResponseBankAcct.getId());
        System.out.println("Test findOneByUser Pass");
    }
}