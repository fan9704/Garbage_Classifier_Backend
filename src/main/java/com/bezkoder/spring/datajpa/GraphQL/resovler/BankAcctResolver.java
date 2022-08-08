package com.bezkoder.spring.datajpa.GraphQL.resovler;

import com.bezkoder.spring.datajpa.model.Bank_acct;
import com.bezkoder.spring.datajpa.model.Bank_type;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.BankTypeRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import com.coxautodev.graphql.tools.GraphQLResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankAcctResolver implements GraphQLResolver<Bank_acct> {
    @Autowired
    private BankTypeRepository bankTypeRepository;
    @Autowired
    private UserRepository userRepository;


    public Bank_type getBankType(Bank_acct bank_acct){
        return bankTypeRepository.findById(bank_acct.getBank_type().getId()).get();
    }
    public User getUser(Bank_acct bankAcct){
        return userRepository.findById(bankAcct.getUser().getId()).get();
    }
}
