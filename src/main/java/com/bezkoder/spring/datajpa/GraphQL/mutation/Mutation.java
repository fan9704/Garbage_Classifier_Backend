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



    public Bank_type createBankType(String bank_name, String bank_code) {
        return bankTypeRepository.save(new Bank_type(bank_name,bank_code));
    }
    public boolean deleteBankType(Long id) {
        bankTypeRepository.deleteById(id);
        return true;
    }
    public Bank_type updateBankType(Long id, String bank_name, String bank_code) throws Exception {
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


}