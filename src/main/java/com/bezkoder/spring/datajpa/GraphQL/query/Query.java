package com.bezkoder.spring.datajpa.GraphQL.query;

import com.bezkoder.spring.datajpa.model.Bank_acct;
import com.bezkoder.spring.datajpa.model.Bank_type;
import com.bezkoder.spring.datajpa.model.Garbage_type;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.BankAcctRepository;
import com.bezkoder.spring.datajpa.repository.BankTypeRepository;
import com.bezkoder.spring.datajpa.repository.GarbageTypeRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Query implements GraphQLQueryResolver{
    @Autowired
    private BankTypeRepository bankTypeRepository;
    @Autowired
    private BankAcctRepository bankAcctRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GarbageTypeRepository garbageTypeRepository;

    public List<Bank_type> findAllBankType() {
        return bankTypeRepository.findAll();
    }
    public List<Bank_acct> findAllBankAcct() {
        return bankAcctRepository.findAll();
    }
    public List<User> findAllUser() {
        return userRepository.findAll();
    }
    public List<Garbage_type> findAllGarbageType(){
        return garbageTypeRepository.findAll();
    }
}
