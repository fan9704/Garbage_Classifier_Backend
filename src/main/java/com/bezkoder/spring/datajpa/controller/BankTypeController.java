package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Bank_type;
import com.bezkoder.spring.datajpa.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BankTypeController {

    @Autowired
    private BankTypeService bank_typeService;
    @GetMapping("/bank_types")
    public List<Bank_type> allBank_types() {
        return bank_typeService.findAll();
    }
    @GetMapping("/bank_type/{id}")
    public ResponseEntity<Bank_type> getBank_typeById(@PathVariable("id") long id) {
        return bank_typeService.getBank_typeById(id);
    }
    @PostMapping("/bank_type")
    public ResponseEntity<Bank_type> createBank_type(@RequestBody Bank_type bank_type) {
        return bank_typeService.createBank_type(bank_type);
    }
    @PatchMapping("/bank_type/{id}")
    public ResponseEntity<Bank_type> patchBank_type(@PathVariable("id") long id, @RequestBody Bank_type bank_type) {
        return bank_typeService.patchBank_type(id,bank_type);
    }
    @PutMapping("/bank_type/{id}")
    public ResponseEntity<Bank_type> updateBank_type(@PathVariable("id") long id, @RequestBody Bank_type bank_type) {
        return bank_typeService.updateBank_type(id,bank_type);
    }
    @DeleteMapping("/bank_type/{id}")
    public void delete(@PathVariable String id) {
        bank_typeService.deleteById(Long.parseLong(id));
    }
}