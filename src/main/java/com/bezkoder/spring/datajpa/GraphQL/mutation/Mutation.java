package com.bezkoder.spring.datajpa.GraphQL.mutation;

import com.bezkoder.spring.datajpa.model.Bank_acct;
import com.bezkoder.spring.datajpa.model.Bank_type;
import com.bezkoder.spring.datajpa.model.Garbage_type;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.BankAcctRepository;
import com.bezkoder.spring.datajpa.repository.BankTypeRepository;
import com.bezkoder.spring.datajpa.repository.GarbageTypeRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Mutation implements GraphQLMutationResolver {
    @Autowired
    private BankTypeRepository bankTypeRepository;
    @Autowired
    private BankAcctRepository bankAcctRepository;
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  GarbageTypeRepository garbageTypeRepository;


    public Bank_type createBankType(String bank_name, String bank_code) {
        return bankTypeRepository.save(new Bank_type(bank_name,bank_code));
    }
    public boolean deleteBankType(long id) {
        bankTypeRepository.deleteById(id);
        return true;
    }
    public Bank_type updateBankType(long id, String bank_name, String bank_code) throws Exception {
        Optional<Bank_type> BankTypeOptional = bankTypeRepository.findById(id);
        if (BankTypeOptional.isPresent()) {
            Bank_type bankType = BankTypeOptional.get();
            if (bank_name != null)
                bankType.setBank_name(bank_name);
            if (bank_code != null)
                bankType.setBank_code(bank_code);
            bankTypeRepository.save(bankType);
            return bankType;
        }else{
            throw new Exception("Not Found BankType");
        }
    }

    public Bank_acct createBankAcct(long bankType, String AccountCode,long userId) {
        Optional<Bank_type> BankTypeOptional = bankTypeRepository.findById(bankType);
        Optional<User> UserOptional = userRepository.findById(userId);
        if(BankTypeOptional.isPresent()&&UserOptional.isPresent()){
            return bankAcctRepository.save(new Bank_acct(BankTypeOptional.get(),AccountCode,UserOptional.get()));
        }else{
            return null;
        }
    }
    public boolean deleteBankAcct(long id) {
        bankAcctRepository.deleteById(id);
        return true;
    }
    public Bank_acct updateBankAcct(long bankType, String AccountCode, long userId) throws Exception {
        Optional<Bank_acct> BankAcctOptional = bankAcctRepository.findById(bankType);
        Optional<User> UserOptional = userRepository.findById(userId);
        if (BankAcctOptional.isPresent()&& UserOptional.isPresent()) {
            Bank_acct bankAcct = BankAcctOptional.get();
            bankAcct.setUser(UserOptional.get());
            if (AccountCode != null)
                bankAcct.setAccount_code(AccountCode);
            return bankAcctRepository.save(bankAcct);
        }else{
            throw new Exception("Not Found BankAcct");
        }
    }

    public User findUserById(long id){
        Optional<User> UserOptional = userRepository.findById(id);
        if(UserOptional.isPresent()){
            return  UserOptional.get();
        }else{
            return null;
        }
    }

    public Garbage_type findGarbageTypeById(long id){
        Optional<Garbage_type> GarbageTypeOptional = garbageTypeRepository.findById(id);
        if(GarbageTypeOptional.isPresent()){
            return GarbageTypeOptional.get();
        }else {
            return null;
        }
    }
}