package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.dto.Bank_acctDTO;
import com.bezkoder.spring.datajpa.model.Bank_acct;
import com.bezkoder.spring.datajpa.model.Bank_type;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.BankAcctRepository;
import com.bezkoder.spring.datajpa.repository.BankTypeRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class  BankAcctService {
    @Autowired
    private BankAcctRepository bankAcctRepository;
    @Autowired
    private BankTypeRepository bankTypeRepository;
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Bank_acct> getBank_acctByUsername(String username) {
        try{
            User userData = userRepository.findByUserName(username);
            Bank_acct bank_acct =bankAcctRepository.findOneByUser(userData);
            return new ResponseEntity<>( bank_acct, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Bank_acct> getBank_acctById( long id) {
        Optional<Bank_acct> bank_acctData = bankAcctRepository.findById(id);

        if ( bank_acctData.isPresent()) {
            return new ResponseEntity<>( bank_acctData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Bank_acct> createBank_acct(Bank_acctDTO bank_acctDTO) {
        try {
            Optional<User> _user = userRepository.findById(bank_acctDTO.getUser());
            Optional<Bank_type> _bank_type =bankTypeRepository.findById(bank_acctDTO.getBank_type());
            if(_bank_type.isPresent() && _user.isPresent()){
                Bank_acct _bank_acct = bankAcctRepository
                        .save(new Bank_acct(_bank_type.get(), bank_acctDTO.getAccount_code(),_user.get()));
                return new ResponseEntity<>(_bank_acct, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Bank_acct> patchBank_acct(long id,Bank_acct bank_acct) {
        Optional<Bank_acct> bank_acctData = bankAcctRepository.findById(id);

        if (bank_acctData.isPresent()) {
            System.out.println(bank_acct.getBank_type());//Check Variable
            System.out.println(bank_acct.getAccount_code());
            Bank_acct _bank_acct = bank_acctData.get();
            if(bank_acct.getBank_type()!=null ){
                _bank_acct.setBank_type(bank_acct.getBank_type());
            }
            if(bank_acct.getAccount_code()!="" ){
                _bank_acct.setAccount_code(bank_acct.getAccount_code());
            }
            return new ResponseEntity<>(bankAcctRepository.save(_bank_acct), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Bank_acct> patchUserBankAcct(Bank_acctDTO bank_acctDTO) {
        try{
            Optional<User> _user=userRepository.findById(bank_acctDTO.getUser());
            if (_user.isPresent()) {
                Bank_acct bank_acct = bankAcctRepository.findOneByUser(_user.get());
                bank_acct.setBank_type(bankTypeRepository.findById(bank_acctDTO.getBank_type()).get());
                bank_acct.setAccount_code(bank_acctDTO.getAccount_code());
                return new ResponseEntity<>(bankAcctRepository.save(bank_acct), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Bank_acct> updateBank_acct(long id,Bank_acct bank_acct) {
        Optional<Bank_acct> bank_acctData = bankAcctRepository.findById(id);

        if (bank_acctData.isPresent()) {
            Bank_acct _bank_acct = bank_acctData.get();
            _bank_acct.setBank_type(bank_acct.getBank_type());
            _bank_acct.setAccount_code(bank_acct.getAccount_code());
            return new ResponseEntity<>(bankAcctRepository.save(_bank_acct), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //Utils Layer
    public List<Bank_acct> findAll() {
        List<Bank_acct> bank_accts = new ArrayList<Bank_acct>();
        bankAcctRepository.findAll().forEach(e -> bank_accts.add(e));
        return bank_accts;
    }
    public ResponseEntity<String> deleteById(Long userId) {
        bankAcctRepository.deleteById(userId);
        return new ResponseEntity<>("Delete Complete", HttpStatus.OK);
    }
}