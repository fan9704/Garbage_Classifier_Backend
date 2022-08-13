package com.bezkoder.spring.datajpa.repository;

import com.bezkoder.spring.datajpa.configuration.DaoConfig;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ContextConfiguration(classes = DaoConfig.class)
public class TransferMoneyRecordRepositoryTest {
}
