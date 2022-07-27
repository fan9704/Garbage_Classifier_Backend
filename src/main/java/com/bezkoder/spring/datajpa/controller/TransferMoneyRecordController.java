package com.bezkoder.spring.datajpa.controller;

import com.bezkoder.spring.datajpa.model.Transfer_money_record;
import com.bezkoder.spring.datajpa.dto.Transfer_money_recordDTO;
import com.bezkoder.spring.datajpa.service.TransferMoneyRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransferMoneyRecordController {

    @Autowired
    private TransferMoneyRecordService transfer_money_recordService;
    @GetMapping("/transfer_money_records")
    public List<Transfer_money_record> allTransfer_money_records() {
        return transfer_money_recordService.findAll();
    }
    @GetMapping("/transfer_money_record/{id}")
    public ResponseEntity<Transfer_money_record> getTransfer_money_recordById(@PathVariable("id") long id) {
        return transfer_money_recordService.getTransfer_money_recordById(id);
    }
    @PostMapping("/transfer_money_record")
    public ResponseEntity<Transfer_money_record> createTransfer_money_record(@RequestBody Transfer_money_recordDTO transfer_money_recordDTO) {
        return transfer_money_recordService.createTransfer_money_record(transfer_money_recordDTO);
    }
    @PatchMapping("/transfer_money_record/{id}")
    public ResponseEntity<Transfer_money_record> patchTransfer_money_record(@PathVariable("id") long id, @RequestBody Transfer_money_recordDTO transfer_money_recordDTO) {
        return transfer_money_recordService.patchTransfer_money_record(id,transfer_money_recordDTO);
    }
    @PutMapping("/transfer_money_record/{id}")
    public ResponseEntity<Transfer_money_record> updateTransfer_money_record(@PathVariable("id") long id, @RequestBody Transfer_money_recordDTO transfer_money_recordDTO) {
        return transfer_money_recordService.updateTransfer_money_record(id,transfer_money_recordDTO);
    }
    @DeleteMapping("/transfer_money_record/{id}")
    public void delete(@PathVariable String id) {
        transfer_money_recordService.deleteById(Long.parseLong(id));
    }
}