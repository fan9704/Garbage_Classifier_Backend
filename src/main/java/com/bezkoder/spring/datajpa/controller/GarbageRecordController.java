package com.bezkoder.spring.datajpa.controller;
import com.bezkoder.spring.datajpa.dto.Garbage_recordDTO;
import com.bezkoder.spring.datajpa.model.*;
import com.bezkoder.spring.datajpa.service.GarbageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api")
public class GarbageRecordController {

    @Autowired
    private GarbageRecordService garbage_recordService;
    @GetMapping("/garbage_records")
    public List<Garbage_record> allGarbage_records() {
        return garbage_recordService.findAll();
    }

    @GetMapping("/garbage_record/{id}")
    public ResponseEntity<Garbage_record> getGarbage_recordById(@PathVariable("id") long id) {
        return garbage_recordService.getGarbage_recordById(id);
    }

    @PostMapping("/garbage_record")
    public ResponseEntity<Garbage_record> createGarbage_record(@RequestBody Garbage_recordDTO garbage_recordDTO) {
        return garbage_recordService.createGarbage_record(garbage_recordDTO);
    }

    @PatchMapping("/garbage_record/{id}")
    public ResponseEntity<Garbage_record> patchGarbage_record(@PathVariable("id") long id, @RequestBody Garbage_record garbage_record) {
        return garbage_recordService.patchGarbage_record(id,garbage_record);
    }

    @PutMapping("/garbage_record/{id}")
    public ResponseEntity<Garbage_record> updateGarbage_record(@PathVariable("id") long id, @RequestBody Garbage_record garbage_record) {
        return garbage_recordService.updateGarbage_record(id,garbage_record);
    }
    @DeleteMapping("/garbage_record/{id}")
    public void delete(@PathVariable String id) {
        garbage_recordService.deleteById(Long.parseLong(id));
    }
}