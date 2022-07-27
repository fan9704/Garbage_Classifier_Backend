package com.bezkoder.spring.datajpa.service;

import com.bezkoder.spring.datajpa.model.Bank_type;
import com.bezkoder.spring.datajpa.repository.BankTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class  BankTypeService {

    @Autowired
    private BankTypeRepository bankTypeRepository;
    public List<Bank_type> findAll() {


        List<Bank_type> bank_types = new ArrayList<Bank_type>();
        bankTypeRepository.findAll().forEach(e -> bank_types.add(e));

        return bank_types;
    }
    public ResponseEntity<Bank_type> getBank_typeById(long id) {
        Optional<Bank_type> bank_typeData = bankTypeRepository.findById(id);

        if ( bank_typeData.isPresent()) {
            return new ResponseEntity<>( bank_typeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Bank_type> createBank_type(Bank_type bank_type) {
        try {
            Bank_type _bank_type = bankTypeRepository
                    .save(new Bank_type(bank_type.getBank_name(), bank_type.getBank_code()));
            return new ResponseEntity<>(_bank_type, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Bank_type> patchBank_type(long id,Bank_type bank_type) {
        Optional<Bank_type> bank_typeData = bankTypeRepository.findById(id);
        if (bank_typeData.isPresent()) {
            Bank_type _bank_type = bank_typeData.get();
            if(bank_type.getBank_name()!=null || bank_type.getBank_name()!= ""){
                _bank_type.setBank_name(bank_type.getBank_name());
            }
            if(bank_type.getBank_code()!="" ){
                _bank_type.setBank_code(bank_type.getBank_code());
            }
            return new ResponseEntity<>(bankTypeRepository.save(_bank_type), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<Bank_type> updateBank_type(long id,Bank_type bank_type) {
        Optional<Bank_type> bank_typeData = bankTypeRepository.findById(id);

        if (bank_typeData.isPresent()) {
            System.out.println(bank_type.getBank_name()+bank_type.getBank_code());//Check Variable
            Bank_type _bank_type = bank_typeData.get();
            _bank_type.setBank_name(bank_type.getBank_name());
            _bank_type.setBank_code(bank_type.getBank_code());
            return new ResponseEntity<>(bankTypeRepository.save(_bank_type), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //Utils Layer
    public Long count() {

        return bankTypeRepository.count();
    }
    public void deleteById(Long userId) {

        bankTypeRepository.deleteById(userId);
    }
}