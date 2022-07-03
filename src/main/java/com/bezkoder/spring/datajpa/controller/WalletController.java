package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Wallet;
import com.bezkoder.spring.datajpa.dto.WalletDTO;
import com.bezkoder.spring.datajpa.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class WalletController {
    @Autowired
    private WalletService walletService;
    @GetMapping("/walletInfo/{username}")
    public ResponseEntity<List<Wallet>> getWalletRecordByUsername(@PathVariable("username") String username){
        return walletService.getWalletRecordByUsername(username);
    }
    @GetMapping("/walletValue/{username}")
    public ResponseEntity<BigDecimal> getWalletValueByUsername(@PathVariable("username") String username){
        return walletService.getWalletValueByUsername(username);
    }
    @PostMapping("/wallet")
    public ResponseEntity<List<Wallet>> createWalletRecord(@RequestBody WalletDTO walletDTO){
        return walletService.createWalletRecord(walletDTO);
    }

}
