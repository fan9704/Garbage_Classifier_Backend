package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Bank_acct;
import com.bezkoder.spring.datajpa.model.Bank_acctDTO;
import com.bezkoder.spring.datajpa.model.Bank_type;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.BankAcctRepository;
import com.bezkoder.spring.datajpa.repository.BankTypeRepository;
import com.bezkoder.spring.datajpa.repository.UserRepository;
import com.bezkoder.spring.datajpa.service.BankAcctService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BankAcctController {

    @Autowired
    private BankAcctService bank_acctService;
    @Autowired
    private BankAcctRepository bank_acctRepository;
    @Autowired
    private BankTypeRepository bankTypeRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/bank_accts")
    public List<Bank_acct> allBank_accts() {

        return bank_acctService.findAll();
    }

    @GetMapping("/bank_acct/{id}")
    public ResponseEntity<Bank_acct> getBank_acctById(@PathVariable("id") long id) {
        Optional<Bank_acct> bank_acctData = bank_acctRepository.findById(id);

        if ( bank_acctData.isPresent()) {
            return new ResponseEntity<>( bank_acctData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/bank_acct")
    public ResponseEntity<Bank_acct> createBank_acct(@RequestBody Bank_acctDTO bank_acctDTO) {
        try {
            Optional<User> _user = userRepository.findById(bank_acctDTO.getUser());
           Optional<Bank_type> _bank_type =bankTypeRepository.findById(bank_acctDTO.getBank_type());
            if(_bank_type.isPresent() && _user.isPresent()){
                Bank_acct _bank_acct = bank_acctRepository
                        .save(new Bank_acct(_bank_type.get(), bank_acctDTO.getAccount_code(),_user.get()));
                return new ResponseEntity<>(_bank_acct, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/bank_acct/{id}")
    public ResponseEntity<Bank_acct> patchBank_type(@PathVariable("id") long id, @RequestBody Bank_acct bank_acct) {
        Optional<Bank_acct> bank_acctData = bank_acctRepository.findById(id);

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
            return new ResponseEntity<>(bank_acctRepository.save(_bank_acct), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PatchMapping("/bank_acct/user/")
    public ResponseEntity<Bank_acct> patchUserBankAcct(@RequestBody Bank_acctDTO bank_acctDTO) {
        try{
            Optional<User> _user=userRepository.findById(bank_acctDTO.getUser());
            if (_user.isPresent()) {
                Bank_acct bank_acct = bank_acctRepository.findOneByUser(_user.get());
                bank_acct.setBank_type(bankTypeRepository.findById(bank_acctDTO.getBank_type()).get());
                bank_acct.setAccount_code(bank_acctDTO.getAccount_code());
                return new ResponseEntity<>(bank_acctRepository.save(bank_acct), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping("/bank_acct/{id}")
    public ResponseEntity<Bank_acct> updateBank_type(@PathVariable("id") long id, @RequestBody Bank_acct bank_acct) {
        Optional<Bank_acct> bank_acctData = bank_acctRepository.findById(id);

        if (bank_acctData.isPresent()) {
            System.out.println(bank_acct.getBank_type());//Check Variable
            System.out.println(bank_acct.getAccount_code());
            Bank_acct _bank_acct = bank_acctData.get();
            _bank_acct.setBank_type(bank_acct.getBank_type());
            _bank_acct.setAccount_code(bank_acct.getAccount_code());
            return new ResponseEntity<>(bank_acctRepository.save(_bank_acct), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/bank_acct/{id}")
    public void delete(@PathVariable String id) {
        bank_acctService.deleteById(Long.parseLong(id));
    }
}